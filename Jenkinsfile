pipeline 
{
    agent any
    
    tools{
        maven 'maven'
        }

    stages 
    {
        stage('Build') 
        {
             steps {
                bat 'mvn clean install 
            }
        }

        stage('Test') {
            steps {
                bat "mvn clean test
            }
        }
        
          
        stage("Deploy to QA"){
            steps{
                echo("deploy to qa")
            }
        }
        
                
        stage('Regression Automation Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/durgaselenium/PlaywrightPageObjectModel11.git'
                    sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml"
                    
                }
            }
        }  
        
        stage('Publish Extent Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: true, 
                                  reportDir: 'reports', 
                                  reportFiles: 'TestExecutionReport.html', 
                                  reportName: 'HTML Regression Extent Report', 
                                  reportTitles: ''])
            }
        }
        
    }
}