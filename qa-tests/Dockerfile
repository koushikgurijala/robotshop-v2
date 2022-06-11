FROM blazemeter/taurus:latest
RUN echo "Prepping Taurus"
RUN echo "deb [signed-by=/usr/share/keyrings/cloud.google.gpg] http://packages.cloud.google.com/apt cloud-sdk main" | tee -a /etc/apt/sources.list.d/google-cloud-sdk.list && curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key --keyring /usr/share/keyrings/cloud.google.gpg  add - && apt-get update -y && apt-get install google-cloud-cli -y

# # Setup Google Service Account
# COPY service-account.json /etc/
# ENV GOOGLE_APPLICATION_CREDENTIALS="/etc/service-account.json"

# RUN gcloud auth activate-service-account --key-file=${GOOGLE_APPLICATION_CREDENTIALS}

RUN  rm -rf /scripts
COPY scripts /scripts/
USER root
RUN chmod +x -R /scripts
RUN echo "ALL DONE NOW"

