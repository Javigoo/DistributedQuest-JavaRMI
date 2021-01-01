from rest_framework import generics, filters
from .models import *
from .serializers import *

# Create your views here.
class ExamList(generics.ListCreateAPIView):
    search_fields = ['']
    serializer_class = ExamSerializer