from rest_framework import serializers
from .models import *

class ExamSerializer(serializers.Serializer):
    description = serializers.CharField(max_length=MAX_DESCRIPTION_LENGTH)
    date = serializers.DateField(required=False, format="%d-%m-%Y",)
    time = serializers.DateTimeField(required=False, format="%Y-%m-%dT%H:%M:%S")
    location = serializers.CharField(max_length=MAX_UBICATION_LENGTH)

    def create(self, validated_data):
        return Exam.objects.create(**validated_data)

class GradeSerializer(serializers.Serializer):
    class Meta:
        model = StudentExam
        fields = '__all__'
        
    universityId = serializers.CharField(max_length=UNIVERSITY_ID_LENGTH)
    grade = serializers.IntegerField()