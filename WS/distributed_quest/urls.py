from django.urls import path
from rest_framework.generics import ListCreateAPIView
from .views import *

app_name = "distributed_quest"

urlpatterns = [
    path('exams/', ExamList.as_view()),
    path('exams/<int:key>/', ExamView.as_view()),
    path('exams/search/', ExamQuery.as_view()), 
    path('grades/', GradesList.as_view()),
    path('exams/<int:key>/grades/', GradesView.as_view()) 
]
