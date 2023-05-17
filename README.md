# ProjetoTarefa-Spring

## Primeiro começamos abrindo o cockpit e a VM do Fedora-37.
## Após isso subimos o container do MYSQL para conseguir entrar no MYSQLWorkBench, para saber o container que é para subir aplicamos o comando "docker ps -a",
## agora para rodar o container utilizamos o comando "docker start nome_do_container".


# INSTALAÇÃO/CONFIGURAÇÃO DO OPENJDK

## Para a instalação utilizamos o comando "wget https://download.java.net/openjdk/jdk17/ri/openjdk-17+35_linux-x64_bin.tar.gz", e depois extraimos o download com comando
## "tar xvf openjdk-17+35_linux-x64_bin.tar.gz".
## Após a instalação do OPENJDK configuramos ele com o comando "sudo tee /etc/profile.d/jdk17.sh <<EOF":
export JAVA_HOME=/opt/jdk-17
export PATH=\$PATH:\$JAVA_HOME/bin

## Criamos o arquivo, verificamos o comando com "source /etc/profile.d/jdk17.sh". E confirmamos a versão do java com "echo $JAVA_HOME" e "java -version".


# INSTALAÇÃO/CONFIGURAÇÃO DO MAVEN

## Para a instalação do maven utilizamos o comando "wget https://dlcdn.apache.org/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.tar.gz",
## após a instalação extraimos o arquivo com o comando "sudo tar xzf apache-maven-3.8.5-bin.tar.gz -C /opt".

## Após o processo de instalação vamos configurar as variáveis de ambiente criando um novo arquivo com o comando "sudo vi /etc/profile.d/maven.sh",
## e dentro desse arquivo adicionamos o seguinte código:
export M2_HOME=/opt/maven
export PATH=${M2_HOME}/bin:${PATH}

## Agora carregamos as variáveis de ambiente com o comando "source /etc/profile.d/maven.sh",
## verifique a versão com comando "mvn -version".


# INSTALAÇÃO/CONFIGURAÇÃO DO JENKINS

## Primeiro começamos instalando com o seguinte comando "sudo wget -O /etc/yum.repos.d/jenkins.repo \
    https://pkg.jenkins.io/redhat-stable/jenkins.repo".
    
## Após o processo de instalação, importamos com o seguinte comando "sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key",
## e atualizamos o fedora com o comando "sudo dnf upgrade".

## Agora colocaremos a dependências do jenkins que é *OBRIGATÓRIO*, com os comandos "sudo dnf install java-11-openjdk",
## "sudo dnf install jenkins" e "sudo systemctl daemon-reload".

## Agora para iniciar e rodar o jenkins, comando para ativar o serviço do jenkins "sudo systemctl enable jenkins",
## comando para iniciar o serviço do jenkins "sudo systemctl start jenkins", e para saber se está rodando ou não utilizamos o comando "sudo systemctl status jenkins".

## Após todo esse processo o firewall vai estar ligado, por isso utilizamos o código "sudo firewall-cmd--add-port=8080/tcp--permanent".

## Agora entraremos no site do jenkins com o link "http://127.0.0.1:8080", e para conseguir entrar e instalar o jenkins,
## precisamos descobrir a chave criptografada que o próprio jenkins fala o caminho para a pasta.
## Utilizamos o comando "sudo cat *nome_do_arquivo* para saber a chave criptografada e instalamos.

## Dentro do jenkins criamos e configuramos o pipeline com o seguinte código/script:
pipeline{
    agent any
    tools{
        maven 'maven_3_8_5'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Gusta011/ProjetoTarefa-Spring']])
                sh 'mvn clean install'
            }
        }
        stage('Gerar Docker Image'){
            steps{
                script{
                    sh 'docker rm -f senac-tarefa'
                    sh 'docker rmi senac/app-tarefa'
                    sh 'docker build -t senac/app-tarefa .'
                    
                }
            }
        }
        stage('Gerar Container Docker'){
            steps{
                script{
                    sh 'docker rm -f senac-tarefa'
                    sh 'docker run --name senac-tarefa -p 8095:8095 -d senac/app-tarefa'
                }
            }
        }
        
    }
}







