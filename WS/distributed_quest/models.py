from django.db import models

MAX_DESCRIPTION_LENGTH = 500
MAX_UBICATION_LENGTH = 300
UNIVERSITY_ID_LENGTH = 20

# Create your models here.
class Exam(models.Model):
    key = models.AutoField(primary_key=True)
    description = models.CharField(max_length=MAX_DESCRIPTION_LENGTH)
    date = models.DateField()
    time = models.DateTimeField(blank=True, null=True)
    location = models.CharField(max_length=MAX_UBICATION_LENGTH)
    
    def __str__(self):
        return str(self.key)

class StudentExam(models.Model):
    universityId = models.CharField(primary_key=True, max_length=UNIVERSITY_ID_LENGTH)
    exam = models.ForeignKey(Exam, on_delete=models.CASCADE)
    grade = models.IntegerField(blank=True, null=True)

    def __str__(self):
        return self.universityId
