#!/usr/bin/env python3

import json
import requests

URL = "http://127.0.0.1:8000/"
EXAM_GRADES_ID = "37"
EXAM_ID = "1"

def request(method, url, data=None):
    global EXAM_ID

    if method == "get":
        if data is None:
            print("\n"+method.upper(), url)
            response = requests.get(url=URL+url)
        else:
            return
            print("\n"+method.upper(), url, data)
            response = requests.get(url=URL+url)
            
    elif method == "post":
        print("\n"+method.upper(), url, data)
        response = requests.post(url=URL+url, data=data)
        EXAM_ID = str(response.json().get("key"))

    elif method == "put":
        print("\n"+method.upper(), url, data)
        response = requests.put(url=URL+url, data=data)

    elif method == "delete":
        print("\n"+method.upper(), url)
        response = requests.delete(url=URL+url)

    try:
        print(json.dumps(json.loads(response.text), indent=2))
    except:
        print(response)
        if "grades" in url:
            print(json.dumps(json.loads(requests.get(url=URL+"grades/").text), indent=2))
        else:
            print(json.dumps(json.loads(requests.get(url=URL+"exams/").text), indent=2))

def main():
    exam = {"description": "Exam description", "date": "2021-01-01", "time": "2021-01-08T19:37:25", "location": "1233"}
    exam_description = "Exam description"
    exam_new_description = {"description": "New description"}
    grade = {"grade": "7"}

    # Basic Functions
    request("post", "exams/", exam) # Store in the global directory (WS) exams description, date, time and location
    request("get", "exams/") # Download exams information
    request("get", "exams/"+EXAM_ID+"/") # Search exam content using the key 
    request("get", "exams/search/", exam_description) # search them using textual description (full/partial search).
    request("put", "exams/"+EXAM_ID+"/", exam_new_description) # Modify exam’s description.
    request("delete", "exams/"+EXAM_ID+"/") # Delete exam (if it has no grades).

    # Advanced Functions
    request("get", "grades/") # Download student’s grades.
    request("put", "exams/"+EXAM_GRADES_ID+"/grades/", grade) # Upload grades to an exam.
    # Manage student’s access (by ID).
    # Store and retrieve all of your exams' information in a database in your WS server.

if __name__ == "__main__":
    main()


"""
curl -H "Content-Type: application/json" \
    -R POST \
    -D '{"description": "Exam description", "date": "2021-01-01", "time": "2021-01-08T19:37:25", "location": "1233"}' \
    http://127.0.0.1:8000/exams/

curl -X GET 127.0.0.1:8000/exams/  

curl -X DELETE 127.0.0.1:8000/exams/1/ 

curl -X PUT -d "description"="New description" http://127.0.0.1:8000/exams/1/

curl -X PUT -d "grade"="9" http://127.0.0.1:8000/exams/37/grades/

curl -X PUT -H "Content-Type: application/json" -d '{"grade": "10"}' http://127.0.0.1:8000/exams/37/grades/

"""
