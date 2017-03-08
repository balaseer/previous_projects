#ifndef JOBS_H
#define JOBS_H

struct jobs{

				int which;
				pid_t pid;
				char *toDo;
};
typedef struct jobs jobs;
typedef struct jobs *job;
job makeJob(int n, pid_t pid, char *chr);
void freeAll(void *item);
int isEqual(const void *first, const void *second);
int isDone(void *item);
void print(void *item);
char *toString(const void *item);

#endif
