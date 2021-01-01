from django.urls import path
from rest_framework.generics import ListCreateAPIView
from .views import *

app_name = "distributed_quest"

urlpatterns = [
    #path('search/', Query.as_view()),
    path('exams/', ListCreateAPIView.as_view(
        queryset=Exam.objects.all()), serializer_class=ExamSerializer), 
        name='exam-info'
    ),
    path('exams/{int:key}', ListCreateAPIView.as_view(
        queryset=Exam.objects.filter(key=key), serializer_class=ExamSerializer), 
        name='exam-detail'
    ),
]
