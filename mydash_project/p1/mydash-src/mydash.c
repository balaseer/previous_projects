
#include    <sys/types.h>
#include    <sys/wait.h>
#include    "mydash.h"
#include    <unistd.h>
#include    <stdio.h>
#include    <stdlib.h>
#include    <pwd.h>
#include    <readline/readline.h>
#include    <readline/history.h>	
#include    "List.h"
#include    <Node.h>
#include    <jobs.h>


int parseLine(char *line, char **max);
void removeDoneJobs(struct list *list);

int main(int argc, char **argv){

	char    buf[MAXLINE];
	pid_t   pid;
	int     status;
	char *line;
	char *buffer = "";
	char *DASH_PROMPT = "mydash>";
	int bg = 0;
	char *max[2049];


	if(argc == 2){ //without this check, program segfaults when trying to run ./mydash
		if(strcmp(argv[1],"-v") == 0){ //checks git version
			printf("Version:%s\n ", git_version());
			exit(0);
		}	
	}

	List jobs = createList(&isEqual, &toString, &freeAll);//jobs list
	using_history();
	while ((line=readline(DASH_PROMPT))) {
		add_history(line);
		if(line[strlen(line) - 1] == '&'){
			bg = 1;
			buffer = malloc(sizeof(char) * strlen(line) + 1);
			strcpy(buffer, line);
			line[strlen(line) - 1] = '\0';
		}

		parseLine(line, max);

		if(strcmp(line, "") == 0){ //handles empty command
			free(line);
			continue;

		} else if(strcmp(max[0], "exit") == 0){ //handles exit command
			printf("Exitting Successfully\n");
			free(line);
			exit(0);

		}   if(strcmp(max[0], "cd") == 0){ //handles cd command
			if(max[1]){ //cd to specific directory
				chdir(max[1]);
			} else{ //cd to home directory 	
				struct passwd *pw = getpwuid(getuid());
				const char *homedir = pw->pw_dir;
				chdir(homedir);
				free(line);	
				continue;
			} 												
			free(line);
		} else if(strcmp(max[0], "jobs") == 0){ //handles jobs command
			if(jobs->size == 0){
				free(line);
				continue;
			}
			Node node = jobs->head;    //
			while(node->next != NULL){ // these line 
				print(node->obj);  //		print the 
				node = node->next; //			 jobs list	
			}                          
			print(node->obj);
			removeDoneJobs(jobs);
			free(line);
		}else{ //handle all built-in command
			if ( (pid = fork()) < 0)
				err_sys("fork error");

			else if (pid == 0) {        /* child */
				execvp(*max, max);
				err_ret("couldn't execute: %s", buf);
				exit(EXIT_FAILURE);
				/* parent */
				if ( (pid = waitpid(pid, &status, 0)) < 0)
					err_sys("waitpid error");
			}else{
				if(!bg){
					while(wait(&status) != pid);			
				}else{
					int x;
					if(jobs->size == 0){
						x = 1;
					}else{
						x = ((job) (jobs->tail->obj))->which +1;
					}
					job tmp = makeJob(x, pid, buffer);
					Node node = createNode(tmp);
					addAtRear(jobs, node);
					char *temp = toString(tmp);
					printf("%s\n", temp);
					free(temp); 
				}
			}
			free(line);
			bg = 0;
		}

	}
	freeList(jobs);
	free(line);
	exit(EXIT_SUCCESS);

}

int parseLine(char *line, char **max){

	if (!line){
		return 0;
	}
	int i = -1;
	int check;
	while (*line != '\0' && i < 2048) {
		check = 0;
		while (*line == ' ' || *line == '\n') {
			*line++ = '\0';
		}
		*max++ = line;
		while (*line != '\0' && *line != ' ' && *line != '\n') {
			check = 1;
			line++;
		}
		if (check)
			i++;

	}
	max[0] = '\0';		
	return i; 
}

void removeDoneJobs(struct list *list){

	Node node = list->head;
	if (node == NULL) {
		return;
	}
	while (node != NULL) {
		job job =  (node->obj);
		if (isDone(job)) {
			Node copy = node->next;
			freeNode(removeNode(list, node), freeAll);
			node = copy;
		} else {
			node = node->next;
		}
	}
}
