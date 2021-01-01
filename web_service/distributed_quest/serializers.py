from rest_framework import serializers
from .models import *

class ExamSerializer(serializers.Serializer):
    description = serializers.CharField(max_length=models.MAX_DESCRIPTION_LENGTH)
    date = serializers.DateField()
    time = serializers.DateTimeField()
    location = serializers.CharField(max_length=models.MAX_UBICATION_LENGTH)