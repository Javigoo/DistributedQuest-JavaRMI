from django.urls import path
from rest_framework.generics import ListCreateAPIView
from .views import *

app_name = "distributed_quest"

urlpatterns = [
    path('exams/search/', Query.as_view()),
    path('exams/', ExamList.as_view()),
    path('exams/<int:key>/', ExamDetail.as_view()),
    path('exams/<int:key>/delete/', ExamDelete.as_view()),
    # Add Exam with description, date, time, location.
    path('exams/<int:key>/modify', ExamModify.as_view()),
    path('grades/', GradesList.as_view())
]
