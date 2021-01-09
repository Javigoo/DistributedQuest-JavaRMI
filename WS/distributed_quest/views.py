from rest_framework import generics, filters
from .models import *
from .serializers import *
from rest_framework import status

from rest_framework.views import APIView
from rest_framework.response import Response

from rest_framework import authentication, permissions
from django.contrib.auth.models import User
from django.views.generic import View

# Create your views here.
class ExamList(generics.ListCreateAPIView):
    search_fields = ['description', 'date', 'time', 'location']
    queryset = Exam.objects.all()
    serializer_class = ExamSerializer

class ExamView(APIView):
    def get(self, request, key, format=None):
        return Response(ExamSerializer(Exam.objects.get(key=key)).data)

    def delete(self, request, key, format=None):
        exam = Exam.objects.get(key=key)
        students_for_this_exam = StudentExam.objects.filter(exam=exam)
        for student in students_for_this_exam:
            if student.grade:
                return Response(status=status.HTTP_403_FORBIDDEN) # ??
        exam.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)

    def put(self, request, key, format=None):
        exam = Exam.objects.get(key=key)
        exam.description = request.data.get('description')
        exam.save()
        return Response('Changed to '+exam.description, status=status.HTTP_200_OK)
    
    def post(self, request, format=None):
        serializer = ExamSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class ExamQuery(APIView):
    def get(self, request, format=None):
        return Response(ExamSerializer(
            Exam.objects.filter(description__contains=request.GET.get('q')),
            many=True
        ).data)

class GradesList(generics.ListCreateAPIView):
    search_fields = ['universityId', 'grade']
    queryset = StudentExam.objects.all()
    serializer_class = GradeSerializer

class GradesView(APIView):
    def get(self, request, key, format=None):
        d = lambda grade : {"universityId":grade['universityId'],"grade":grade['grade']}
        student_list = StudentExam.objects.filter(exam__key=key)
        return Response([d(grade) for grade in GradeSerializer(student_list, many=True).data])

    def post(self, request, key, format=None):
        data = request.data
        data.update({'exam': key})
        serializer = GradeSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)