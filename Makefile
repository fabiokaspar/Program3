CC = gcc
CFLAGS = -Wall -pthread
DEBUGFLAGS = -g 

OBJS = ep3.o
PROGRAM = ep3

all : primeiro 

primeiro: $(OBJS)
	$(CC) $(CFLAGS) $(OBJS) -o $(PROGRAM)

%.o: %.c
	$(CC) $(CFLAGS) $(DEBUGFLAGS) -c $<

clean:
	-rm -f $(OBJS) $(PROGRAM) *~ core*
