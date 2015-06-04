#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <sys/time.h>
#include <unistd.h>

/*********************** VARIAVEIS GLOBAIS *********************************/
FILE* arq;
int r, N;
char criterio;
int *qtoComeu;
int *pesos;

struct timeval inicio, fim;
sem_t mutex;

/*********************** PROTOTIPOS DAS FUNCOES *********************************/
void parserEntrada(int, char**);
void leituraPesos();
void* filosofo(void*);
float tempoDesdeInicio();
/********************************************************************************/

int main(int argc, char** argv){
	gettimeofday(&inicio, NULL);
	parserEntrada(argc, argv);
	fscanf(arq,"%d",&N);

	long i;
	pesos = malloc(N*sizeof(int));
	qtoComeu = malloc(N*sizeof(int));
	leituraPesos();

	pthread_t filosofos[N];

	if(sem_init(&mutex, 0, 1)){
		fprintf(stderr, "erroR creating semaphore\n");
		exit(0);
	}

	for(i = 0; i < N; i++){
	 	if(pthread_create(&filosofos[i], NULL, &filosofo, (void*) i)){
			fprintf(stderr, "erroR creating filosofo\n");
			exit(0);
	 	}
	}

	for(i = 0; i < N; i++){
		if(pthread_join(filosofos[i], NULL)){
			fprintf(stderr, "erroR joining filosofo\n");
			exit(0);	
		}
	}	

	printf("\n***************************\n");
	printf("Quanto cada filosofo comeu:");
	printf("\n***************************\n");

	for(i = 0; i < N; i++){
		printf("Filosofo %ld comeu %d\n", i, qtoComeu[i]);
	}

	pthread_exit(NULL);

	return 0;
}

/*###############################################################################*/

void* filosofo(void* idThread){
	long id = (long) idThread;
	qtoComeu[id] = 2*pesos[id];

	// usleep(4280450);
	// printf("Tempo: %f ID: %ld\n", tempoDesdeInicio(), id);
	
	// ao obter o garfo imprime o id
	printf("O filosofo %ld comecou a comer\n", id);
	
	// ****COME****

	// ao devolver o garfo imprime o id
	printf("O filosofo %ld terminou de comer\n\n", id);

	return NULL;
}

void parserEntrada(int argc, char** argv){
	if(argc != 4){
		printf("Formato esperado: ./ep3 <file> <R> <U|P>\n");
		printf("file := arquivo texto como especificado no enunciado\n");
		printf("R    := quantidade de porcoes comidas\n");
		printf("U|P  := situacao uniforme ou com peso\n");
		exit(-1);
	}
	else{
		arq = fopen(argv[1], "r");
		r = atoi(argv[2]);
		criterio = argv[3][0];
	}
}

void leituraPesos(){
	int i;

	for(i = 0; i < N; i++){
		fscanf(arq,"%d", &pesos[i]);
	}
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

// LEMBRETE:
//
// - N > 2 filosofos
// - cada um dos filosofos tem um peso (> 1)
// - gastam um tempo aleatorio pensando
// - gastam um mesmo tempo comendo
// - cada filosofo eh uma thread
// - garantir justica