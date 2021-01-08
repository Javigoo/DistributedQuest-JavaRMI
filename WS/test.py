#!/usr/bin/env python3

import json
import requests

URL = "http://127.0.0.1:8000/"
EXAM_ID = "6"

def request(method, url, data=None):
    if method == "get":
        if data is None:
            print("\n"+method.upper(), url)
            response = requests.get(url = URL + url)
        else:
            return
            print("\n"+method.upper(), url, data)
            response = requests.get(url = URL + url)
    elif method == "post":
        print("\n"+method.upper(), url, data)
        response = requests.post(url = URL + url, json = data)
        """
        curl --header "Content-Type: application/json" \
            --request POST \
            --data '{"description": "Exam description", "date": "2021-01-01", "time": "2021-01-08T19:37:25", "location": "1233"}' \
            http://127.0.0.1:8000/exams/
        """
    elif method == "put":
        return
        print("\n"+method.upper(), url, data)
        response = requests.put(url = URL + url)
    elif method == "delete":
        print("\n"+method.upper(), url)
        response = requests.delete(url = URL + url)

    try:
        print(json.dumps(json.loads(response.text), indent=2))
    except:
        print(response)

def main():
    exam = "{'description': 'Exam description', 'date': '01-01-2021', 'time': '2021-01-08T19:37:25', 'location': '1233'}"
    exam_description = 'Exam description'
    exam_new_description = 'New exam description'

    # Basic Functions
    request("post", "exams/", exam) # Store in the global directory (WS) exams description, date, time and location
    request("get", "exams/") # Download exams information
    request("get", "exams/"+EXAM_ID+"/") # Search exam content using the key 
    request("get", "exams/search/", exam_description) # search them using textual description (full/partial search).
    request("put", "exams/"+EXAM_ID+"/", exam_new_description) # Modify exam’s description.
    request("delete", "exams/"+EXAM_ID+"/") # Delete exam (if it has no grades).

    # Advanced Functions
    #request("post", "") # Upload grades to an exam.
    request("get", "grades/") # Download student’s grades.
    # Manage student’s access (by ID).
    # Store and retrieve all of your exams' information in a database in your WS server.

if __name__ == "__main__":
    main()


