#!/usr/bin/env python3

import os
import sys
import uuid 
import json
import random
import requests
from datetime import datetime


EXAM_ID = "1"
URL = "http://127.0.0.1:8000/"

def request(method, url, data=None):

    if method == "get":
        if data is None:
            print("\n"+method.upper(), url)
            response = requests.get(url=URL+url)
        else:
            print("\n"+method.upper(), url, data)
            response = requests.get(url=URL+url+"?=="+data)
            
    elif method == "post":
        print("\n"+method.upper(), url, data)
        response = requests.post(url=URL+url, data=data)

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

def main():
    global EXAM_ID
    exam = {"description": "Exam "+EXAM_ID+" description", 
            "date": datetime.now().strftime("%Y-%m-%d"), 
            "time": datetime.now().strftime("%Y-%m-%dT%H:%M:%S"), 
            "location": "8000"
            }
    exam_description = "New exam "+EXAM_ID+" description"
    exam_new_description = {"description": exam_description}

    print("\n### Basic Functions ###")
    request("post", "exams/", exam) # Store in the global directory (WS) exams description, date, time and location
    request("get", "exams/") # Download exams information
    request("put", "exams/"+EXAM_ID+"/", exam_new_description) # Modify exam’s description.
    request("get", "exams/"+EXAM_ID+"/") # Search exam content using the key 
    request("get", "exams/search/", exam_description) # search them using textual description (full/partial search).
    request("delete", "exams/"+EXAM_ID+"/") # Delete exam (if it has no grades).
    
    # Create exam with grades
    EXAM_ID = nextID(EXAM_ID)
    exam_grades = {"description": "Exam "+EXAM_ID+" with grades", "date": datetime.now().strftime("%Y-%m-%d"), "time": datetime.now().strftime("%Y-%m-%dT%H:%M:%S"), "location": "8000"}
    grade = {"universityId": str(uuid.uuid1())[:20], "grade": str(random.randint(0,10))}
    request("post", "exams/", exam_grades)

    print("\n### Advanced Functions ###")
    request("post", "exams/"+EXAM_ID+"/grades/", grade) # Upload grades to an exam.
    request("get", "grades/") # Download student’s grades.
    # Manage student’s access (by ID).
    # Store and retrieve all of your exams' information in a database in your WS server.

def nextID(EXAM_ID):
    EXAM_ID = str(int(EXAM_ID)+1)
    with open('tmp', 'w') as f:
        f.write(EXAM_ID)
    return EXAM_ID

if __name__ == "__main__":

    if len(sys.argv) > 1:
        EXAM_ID = sys.argv[1]
    else:
        if os.path.exists("tmp"):
            with open('tmp', 'r') as f:
                EXAM_ID = str(int(f.read()) + 1)    
    with open('tmp', 'w') as f:
        f.write(EXAM_ID)
    
    main()


"""
curl -H "Content-Type: application/json" \
    -R POST \
    -D '{"description": "Exam with grades", "date": "2021-01-01", "time": "2021-01-01T00:00:00", "location": "1234"}' \
    http://127.0.0.1:8000/exams/

curl -X GET 127.0.0.1:8000/exams/  

curl -X DELETE 127.0.0.1:8000/exams/1/ 

curl -X PUT -d "description"="New description" http://127.0.0.1:8000/exams/1/

curl -X PUT -d "grade"="9" http://127.0.0.1:8000/exams/37/grades/

curl -X PUT -H "Content-Type: application/json" -d '{"grade": "10"}' http://127.0.0.1:8000/exams/1/grades/

"""
