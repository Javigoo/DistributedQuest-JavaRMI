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
            print("\n"+method.upper(), url+data)
            response = requests.get(url=URL+url+data)
            
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
    print("WS DEMO")

    print("\n1- Create 4 different exams, the first and the second with similar description (e.g. Distributed Computing 1 & 2)")
    exam_1_id = getID(EXAM_ID)
    request("post", "exams/", {"description": "Distributed Computing 1 (ID={})".format(exam_1_id), 
                                "date": datetime.now().strftime("%Y-%m-%d"), 
                                "time": datetime.now().strftime("%Y-%m-%dT%H:%M:%S"), 
                                "location": random.randint(1024,49152)
                                })
    exam_2_id = getID(EXAM_ID)
    request("post", "exams/", {"description": "Distributed Computing 2 (ID={})".format(exam_2_id), 
                                "date": datetime.now().strftime("%Y-%m-%d"), 
                                "time": datetime.now().strftime("%Y-%m-%dT%H:%M:%S"), 
                                "location": random.randint(1024,49152)
                                })
    exam_3_id = getID(EXAM_ID)
    request("post", "exams/", {"description": "Exam 3 description (ID={})".format(exam_3_id), 
                                "date": datetime.now().strftime("%Y-%m-%d"), 
                                "time": datetime.now().strftime("%Y-%m-%dT%H:%M:%S"), 
                                "location": random.randint(1024,49152)
                                })
    exam_4_id = getID(EXAM_ID)
    request("post", "exams/", {"description": "Exam 4 description (ID={})".format(exam_4_id), 
                                "date": datetime.now().strftime("%Y-%m-%d"), 
                                "time": datetime.now().strftime("%Y-%m-%dT%H:%M:%S"), 
                                "location": random.randint(1024,49152)
                                })

    print("\n2- Modify 3rd’s description")
    request("put", "exams/"+exam_3_id+"/", {"description": "New exam 3 description!!! (ID={})".format(exam_3_id)})

    print("\n3-Search an exam by its description (full)")
    request("get", "exams/search/?q=", "Distributed Computing 1 (ID={})".format(exam_1_id))

    print("\n4- Delete the forth one")
    request("delete", "exams/"+exam_4_id+"/")

    print("\n5- List all exams")
    request("get", "exams/")

    print("\n6- Upload grades to the first and the second exam")
    id_1 = str(uuid.uuid1())[:20] 
    grade = {"universityId": id_1, "grade": str(random.randint(0,10))}
    request("post", "exams/"+exam_1_id+"/grades/", grade)

    id_2 = str(uuid.uuid1())[:20]
    grade = {"universityId": id_2, "grade": str(random.randint(0,10))}
    request("post", "exams/"+exam_2_id+"/grades/", grade)

    print("\n7- Delete the second")
    request("delete", "exams/"+exam_2_id+"/")

    print("\n8- Search an exam by its description (partial)")
    request("get", "exams/search/?q=", "Computing 1")

    print("\n9- Show its grades")
    request("get", "exams/"+exam_1_id+"/grades/")

    print("\n10- Check some ID from an existent student")
    request("get", "uid/"+id_1)

    print("\n11- Check some other ID")
    request("get", "uid/invalidID") 

def getID(EXAM_ID):
    with open('tmp', 'r') as f:
        EXAM_ID = f.read()
    with open('tmp', 'w') as f:
        f.write(str(int(EXAM_ID)+1))
    return EXAM_ID

if __name__ == "__main__":

    if len(sys.argv) > 1:
        EXAM_ID = sys.argv[1]
    else:
        if os.path.exists("tmp"):
            with open('tmp', 'r') as f:
                EXAM_ID = f.read()
    with open('tmp', 'w') as f:
        f.write(EXAM_ID)
    
    main()
