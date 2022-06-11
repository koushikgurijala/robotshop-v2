echo "Currently running bzt command in Container"
bzt /scripts/test.yml
date_now=$(date "+%F-%H-%M-%S")
echo "The date is $date_now"
mv /tmp/artifacts /tmp/artifacts-$date_now
echo "Script for copying the artifacts to Cloud Storage"
echo "copying..."
gsutil -m cp -r /tmp/artifacts-$date_now gs://robot-shop-taurus
echo "exited...s"