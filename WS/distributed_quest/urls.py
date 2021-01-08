from django.urls import path
from rest_framework.generics import ListCreateAPIView
from .views import *

app_name = "distributed_quest"

urlpatterns = [
    path('exams/', ExamList.as_view()), # OK
    path('exams/<int:key>/', ExamDetail.as_view()), # OK  
    path('exams/search/<str:key>/', Query.as_view()), 
    path('grades/', GradesList.as_view())   # OK
]
