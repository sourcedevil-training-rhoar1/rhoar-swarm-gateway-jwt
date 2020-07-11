#RESULT=`curl -k  -d "client_id=login_client" -d "username=mavenuser" -d "password=mavenuser" -d "grant_type=password" -d "client_secret=e17f3585-2e14-461e-8e2f-6f2a0e962f01" https://sso-demo-sso.10.40.1.253.nip.io/auth/realms/custom_login_realm/protocol/openid-connect/token`
RESULT=`curl -k  -d "client_id=rhoar-swarm-gateway-jwt"  -d "client_secret=fbfdd716-ddb2-44fc-9915-956930fdce4c" -d "username=sourcedeviluser" -d "password=password" -d "grant_type=password" https://sso-sso-evil.10.40.2.222.nip.io/auth/realms/jwt-sourcedevil/protocol/openid-connect/token`

#Revisar contenido del token
echo $RESULT | python -m json.tool

#Separar token
TOKEN=$(echo $RESULT | sed 's/.*access_token":"//g' | sed 's/".*//g')

#curl -X GET  -H "Authorization: Bearer $TOKEN" http://172.30.29.208:8080/maven2/com/myspace/t1/1.0.0-SNAPSHOT/t1-1.0.0-SNAPSHOT.jar
curl -X GET  -H "Authorization: Bearer $TOKEN" http://rhoar-swarm-gateway-jwt-jwt-sourcedevil.10.40.2.222.nip.io/api/secured/raffle-winner

