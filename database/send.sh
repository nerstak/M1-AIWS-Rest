#!/bin/sh
echo "Restarting container...";
docker restart pg-docker;

sleep .5
echo "Sending files...";
for f in scripts/*; 
do 
	docker cp $f pg-docker:/home/; 
done

echo "Setting up database...";
docker exec -w /home pg-docker ./run.sh;