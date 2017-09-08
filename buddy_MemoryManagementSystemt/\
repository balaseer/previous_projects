#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>
#include <pthread.h>
#include "buddy.h"

#define MAX_MEM_SIZE
#define HIGH_EXP 35	
#define LOW_EXP 5

typedef struct {
		unsigned tag;
		unsigned kval;
		void *prev;
		void *next;
} block;

static void *BASE = NULL;
static block *AVAIL[HIGH_EXP + 1];
static unsigned MAX;
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
void *calloc(size_t, size_t);
void *malloc(size_t);
void *realloc(void *ptr, size_t);
void free(void *ptr);
void split(unsigned);
unsigned get_k(size_t);


void buddy_init(size_t initBytes){

		unsigned shft = HIGH_EXP;
		BASE = sbrk(1UL << shft);
		while (BASE < 0 || errno == ENOMEM) {
				shft--;
				BASE = sbrk(1UL << shft);
				if (BASE < 0 && (initBytes > ((1UL << shft) - sizeof(block)) || shft < LOW_EXP)){
						errno = ENOMEM;
						exit(1);
				}
		}
		MAX = shft;
		AVAIL[shft] = BASE;
		AVAIL[shft]->tag = 1;
		AVAIL[shft]->kval = shft;
		AVAIL[shft]->next = NULL;
		AVAIL[shft]->prev = NULL;

}

void *calloc(size_t num, size_t size){
    void *ptr_calloc;
    pthread_mutex_lock(&mutex);
    ptr_calloc = buddy_calloc(num, size);
    pthread_mutex_unlock(&mutex);
    return ptr_calloc;
}

void *buddy_calloc(size_t num, size_t size){

		if (num == 0 || size == 0){
				return NULL;
		}
		if (!BASE){
				buddy_init((size_t) (size * num));
		}
		void *ret = buddy_malloc(num * size);
		memset(ret, 0, num * size);
		return ret;
}

void *malloc(size_t mem_req){
    void *ptr_malloc;
    if (!BASE) buddy_init(0);
    pthread_mutex_lock(&mutex);
    ptr_malloc = buddy_malloc(mem_req);
    pthread_mutex_unlock(&mutex);
    return ptr_malloc;
}

void *buddy_malloc(size_t mem_req){

		if (!BASE){
				buddy_init((size_t) mem_req);
		}
		unsigned local_k = get_k(mem_req);
		void *ret = NULL;
		if(local_k < MAX && !AVAIL[local_k]){
				split(local_k + 1);
		}
		if(local_k <= MAX && AVAIL[local_k]){
				block *tmp = AVAIL[local_k];
				AVAIL[local_k] = AVAIL[local_k]->next;
				if(AVAIL[local_k]){
						AVAIL[local_k]->prev = NULL;
				}
				tmp->prev = NULL;
				tmp->next = NULL;
				tmp->tag = 0;
				tmp++;
				ret = (void *) tmp;
		}else{
				errno = ENOMEM;
	}
		return ret;
}

void *realloc(void *reall, size_t size){
    void *ptr_realloc;
    pthread_mutex_lock(&mutex);
    ptr_realloc = buddy_realloc(reall, size);
    pthread_mutex_unlock(&mutex);
    return ptr_realloc;
}

void *buddy_realloc(void *reall, size_t size){

		if(!reall){
				return buddy_malloc(size);
		}
		if(size == 0){
				buddy_free(reall);
		}
		if(!BASE){
				buddy_init(size);
		}

		block *TMP = (block *) reall;
		TMP--;
		size_t oldSize = (1 << TMP->kval);
		void *ret;
		if(oldSize < size){
				ret = buddy_malloc(size);
				size = size + sizeof(block);
				size = (1 << get_k(size));
				memcpy(ret, reall, size);
				buddy_free(reall);
		}else{
				ret = reall;
		}
		return ret;
}

void free(void *free_me){
    pthread_mutex_lock(&mutex);
    buddy_free(free_me);
    pthread_mutex_unlock(&mutex);
}

void buddy_free(void *free_me){

		if(!free_me){return;}
		block *TMP = (block *) free_me;
		TMP--;
		unsigned buddy = 0;
		block *BUD = NULL;
		unsigned k = TMP->kval;
		if(k < MAX){
				BUD = (block *) ((((size_t) TMP - (size_t) BASE) ^ ((size_t) 1 << k)) + (size_t) BASE);
				if(k == BUD->kval){
						buddy = BUD->tag;
				}
		}
		if(buddy){
				block *PREV = BUD->prev;
				if(BUD == AVAIL[k]){
						AVAIL[k] = BUD->next;
				}
				if(!PREV){
						AVAIL[k] = BUD->next;
				}else{
						PREV->next = BUD->next;
				}
				block *NEXT = BUD->next;
				if(NEXT){
						NEXT->prev = BUD->prev;
				}
				if(BUD < TMP){
						TMP = BUD;
				}
				k++;
				TMP->kval = k;
				TMP->tag = 0;
				TMP++;
				buddy_free((void *) TMP);
		}else{
				block *curr = (block *) AVAIL[k];
				if(curr){
						while(curr->next){
								curr = curr->next;
						}
						curr->next = TMP;
				}else{
						AVAIL[k] = TMP;
				}
				TMP->kval = k;
				TMP->prev = curr;
				TMP->next = NULL;
				TMP->tag = 1;
		}
}

void split(unsigned k){

		if(!AVAIL[k] && k + 1 <= MAX){
				split(k + 1);
		}
		if(AVAIL[k]){
				block *tmp = AVAIL[k];
				AVAIL[k] = AVAIL[k]->next;
				if(AVAIL[k]){
						AVAIL[k]->prev = NULL;
				}
				AVAIL[k - 1] = tmp;
				AVAIL[k - 1]->tag = 1;
				AVAIL[k - 1]->kval = k - 1;
				AVAIL[k - 1]->prev = NULL;
				block *splt =(block*) ((((size_t) AVAIL[k - 1] - (size_t) BASE) +((size_t) 1 << (k - 1))) + (size_t) BASE);
				AVAIL[k - 1]->next = splt;
				splt->tag = 1;
				splt->kval = k - 1;
				splt->next = NULL;
				splt->prev = AVAIL[k - 1];
		}
}

unsigned get_k(size_t size){

        unsigned k = LOW_EXP;
        size_t sz = (1 << k) - sizeof(block);
        while(size > sz && k < 64){
                sz = ((size_t) 1 << ++k) - sizeof(block);
        }
        return k;
}

void printBuddyLists(void){

		unsigned k = 0;
		unsigned count = 0;
		block *curr = NULL;
		printf("\n");
		while(k <= MAX){
				printf("List %u: head = %p --> ", k, AVAIL + k);
				curr = AVAIL[k];
				while(curr){
						if(curr->tag){
								count++;
						}
						printf("[tag=%u,kval=%u,addr=%p] --> ", curr->tag, curr->kval,curr + 1);
						curr = curr->next;
				}
				printf("<null>\n");
				k++;
		}
		printf("\n Number of available blocks = %u\n", count);
}

