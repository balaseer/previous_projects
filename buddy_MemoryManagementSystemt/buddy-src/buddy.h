
void buddy_init(size_t);
void *buddy_calloc(size_t, size_t);
void *buddy_malloc(size_t);
void *buddy_realloc(void *ptr, size_t);
void buddy_free(void *);
void printBuddyLists(void);
