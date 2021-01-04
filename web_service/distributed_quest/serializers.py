from rest_framework import serializers
from .models import *

class ExamSerializer(serializers.Serializer):
    description = serializers.CharField(max_length=MAX_DESCRIPTION_LENGTH)
    date = serializers.DateField()
    time = serializers.DateTimeField()
    location = serializers.CharField(max_length=MAX_UBICATION_LENGTH)

class GradeSerializer(serializers.Serializer):
    universityId = serializers.CharField(max_length=UNIVERSITY_ID_LENGTH)
    grade = serializers.IntegerField()