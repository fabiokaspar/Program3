#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>
#include <sys/time.h>

#define NUM 5


/************* VARIAVEIS GLOBAIS ***************/
sem_t mutex;
struct timeval inicio, fim;

/************* PROTOTIPOS DE FUNÇÕES ***************/
void* Philosopher(void*);
float tempoDesdeInicio();


int main(int argc, char** argv){
	gettimeofday(&inicio, NULL);
	pthread_t filosofo[NUM];
	int i;

	if(sem_init(&mutex, 0, 1)){
		fprintf(stderr, "erroR creating semaphore\n");
		exit(0);
	}

	for(i = 0; i < NUM; i++){
		if(pthread_create(&filosofo[i], NULL, &Philosopher, (void*) i)){
			fprintf(stderr, "erroR creating Philosopher\n");
			exit(0);
		}
	}

	for(i = 0; i < NUM; i++){
		if(pthread_join(filosofo[i], NULL)){
			fprintf(stderr, "erroR joining Philosopher\n");
			exit(0);
		}
	}


	//printf("Δt = %f seg\n", timedif);

	pthread_exit(NULL);

	return 0;
}

void* Philosopher(void* arg){
	int id = (int) arg;
	printf("ID = %d\n", id);

	while(1){
		usleep(4280450);
		printf("Tempo: %f ID: %d\n", tempoDesdeInicio(), id);
		break;
	}

	return NULL;
}

float tempoDesdeInicio(){
	float timedif;

	sem_wait(&mutex);
	gettimeofday(&fim, NULL);
	timedif = (float)(fim.tv_sec - inicio.tv_sec);
	timedif += (float)(fim.tv_usec - inicio.tv_usec)/1000000;
	sem_post(&mutex);

	return timedif;
}