from django.db import models

MAX_DESCRIPTION_LENGTH = 500
MAX_UBICATION_LENGTH = 300
UNIVERSITY_ID_LENGTH=20

# Create your models here.
class Exam(models.Model):
    key = models.AutoField(primary_key=True)
    description = models.CharField(max_length=MAX_DESCRIPTION_LENGTH)
    date = models.DateField()
    time = models.DateTimeField()
    location = models.CharField(max_length=MAX_UBICATION_LENGTH)
    
    def __str__(self):
        return str(self.key)

class Question(models.Model):
    exam = models.ForeignKey(Exam, on_delete=models.CASCADE)
    correct_choice = models.IntegerField() # No se controlan las opciones a nivel de modelo.
    description = models.CharField(max_length=MAX_DESCRIPTION_LENGTH)

    def __str__(self):
        return self.description

class Choice(models.Model):
    question = models.ForeignKey(Question, on_delete=models.CASCADE)
    c_id = models.IntegerField() # No se controlan las opciones a nivel de modelo.
    description = models.CharField(max_length=MAX_DESCRIPTION_LENGTH)

    def __str__(self):
        return str(self.c_id)

class StudentExam(models.Model):
    universityId = models.CharField(primary_key=True, max_length=UNIVERSITY_ID_LENGTH)
    grade = models.IntegerField(blank=True, null=True)
    time_finish = models.DateTimeField()
    exam = models.ForeignKey(Exam, on_delete=models.CASCADE)

    def __str__(self):
        return self.universityId

class StudentQuestion(models.Model):
    student = models.ForeignKey(StudentExam, on_delete=models.CASCADE)
    question = models.ForeignKey(Question, on_delete=models.CASCADE)
    selected_choice = models.IntegerField() # No se controlan las opciones a nivel de modelo.
    time_sent = models.DateTimeField()

    def __str__(self):
        return str(self.selected_choice)