#include 	<stdlib.h>
#include 	<stdio.h>
#include 	<sys/wait.h>
#include	<sys/types.h>
#include    "jobs.h"
#include	<string.h>

job makeJob(int n, pid_t id, char* chr){
	job tmp = malloc (sizeof(jobs));
	tmp->which = n;
	tmp->pid = id;
	tmp->toDo = chr;
	return tmp;
}

void freeAll(void *item){
	job job =  item;
	if(job == NULL) return;
	free(job->toDo);
	free(job);
}

int isEqual(const void *first, const void *second){	
	job job1 = (job) first;
	job job2 = (job) second;
	return job1->pid == job2->pid;
}

int isDone(void *item){
    job job = item;
    int n = NULL;
    return waitpid(job->pid, &n, WNOHANG);
}

void print(void *item){	
	job job =  item;
	if (isDone(item)){
		printf("[%d] Done %s \n",job->which,job->toDo); 
	} 
	else { printf("[%d] Still Running %s \n",job->which, job->toDo); }
}

char *toString(const void *item){
	job job1 = (job) item;
	char* chr =(char*)malloc(sizeof(char)* (strlen(job1->toDo) + 100));
	sprintf(chr,"[%d] %d %s", job1->which,(int) job1->pid, job1->toDo); 
	return chr;
}
