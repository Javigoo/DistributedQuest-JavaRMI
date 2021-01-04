from rest_framework import generics, filters
from .models import *
from .serializers import *

# Create your views here.
class ExamList(generics.ListCreateAPIView):
    search_fields = ['description', 'date', 'time', 'location']
    queryset = Exam.objects.all()
    serializer_class = ExamSerializer

class ExamDetail(ApiView):
    def get(self, request, key, format=None):
        return Response(Exams.objects.get(key=key))

class Query(ApiView):
    def get(self, request, format=None):
        return Response(Exams.objects.filter(description__contains=request.GET.get('query')))

class ExamModify(ApiView):
    def post(self, request, key, format=None):
        exam = Exams.objects.get(key=key)
        exam.description = request.GET.get('description')
        exam.save()
        return Response(status=status.HTTP_200_OK)

class GradesList(generics.ListCreateAPIView):
    search_fields = ['universityId', 'grade']
    queryset = StudentExam.objects.all()
    serializer_class = GradeSerializer

class ExamDelete(ApiView):
    def delete(self, request, key, format=None):
        exam = Exams.objects.get(key=key)
        students_for_this_exam = StudentExam.objects.filter(exam=exam)
        for student in students_for_this_exam:
            if student['grade']:
                return Response(status=status.HTTP_400) # ??
        exam.delete()
        return Response(status=status.HTTP_204_NO_CONTENT
