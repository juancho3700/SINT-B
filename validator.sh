#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: ./validator.sh <account>"
    exit 1
fi

account=$1
errors=0

postURL=(
    'libros'                # 1 Returns all books
    'libros/autor/4'        # 2 Returns all books from an existing author
    'libro/671'             # 3 Returns an existing book
    'paises'                # 4 Returns all countries
    'pais/T8'               # 5 Returns an existing country
    'autores'               # 6 Returns all authors
    'autor/4'               # 7 Returns an existing author
    'autores/pais/T8'       # 8 Returns all authors from an existing country
    'autores/paises/45'     # 9 Returns all authors from a missing country
    'libros/autor/4L'      # 10 Returns all books from a missing author
    'pais/69'
)

return_codes=(200 200 200 200 200 200 200 200 404 404)

port=$((7000 + account))
preURL="http://localhost:$port/sint$account/P2Lib/v1/"

declare -a url

# Compose all API calls
for ((i=0; i<${#postURL[@]}; i++)); do
    url+=("$preURL${postURL[$i]}")
    # echo "${url[$i]}"
done

for ((i=0; i<${#url[@]}; i++)); do
    # Fetch actual response from sint$account
    response_raw=$(curl -s "${url[$i]}")  
    response=$(echo -n "$response_raw" | sed -e 's/[[:space:]]//g') 
    result_code=$(curl -s -o /dev/null -w "%{http_code}" "${url[$i]}")

    # Fetch expected response from lecturer's account
    expected_result_url="http://luis.sabucedo.webs.uvigo.es/responses/response$((i+1))" 
    expected_result_raw=$(curl -s "$expected_result_url")   
    expected_result=$(echo -n "$expected_result_raw" | sed -e 's/[[:space:]]//g')
    
    echo -n "Test $i. "

    echo "$expected_result" | jq --sort-keys .  > temp1.json
    echo "$response" | jq --sort-keys .  > temp2.json

    if (diff -b temp1.json temp2.json)  &&  ( test "$result_code" = "${return_codes[$i]}" ); then
        echo "PASS"
    else
        echo "FAIL"
        #echo "Got from sint$account:"
        #echo "$response"
        #echo "Expected result (response$((i+1))):"
        #echo "$expected_result_raw"
        ((errors++))  # Increment error count
    fi
    echo "------------------------------------------------------"
done

if [ "$errors" -eq "0" ]; then
    echo "Preliminary assessment OK"
    echo "Proceed to submit your solution to A2"
else
    echo "Preliminary assessment failed"
    echo "Error count: $errors"
fi