from rest_framework import serializers
from .models import *

class ExamSerializer(serializers.Serializer): 
    description = serializers.CharField(max_length=MAX_DESCRIPTION_LENGTH, required=False)
    date = serializers.DateField(required=True, format="%d-%m-%Y",)
    time = serializers.DateTimeField(required=True, format="%Y-%m-%dT%H:%M:%S")
    location = serializers.CharField(max_length=MAX_UBICATION_LENGTH, required=True)
    key = serializers.SerializerMethodField()

    def get_key(self, obj):
        return obj.key

    def create(self, validated_data):
        return Exam.objects.create(**validated_data)

class GradeSerializer(serializers.Serializer):
    universityId = serializers.CharField(max_length=UNIVERSITY_ID_LENGTH, required=True)
    grade = serializers.IntegerField(required=False)
    exam = serializers.SlugRelatedField(queryset=Exam.objects.all(), slug_field='key', required=True)

    def create(self, validated_data):
        return StudentExam.objects.create(**validated_data)
