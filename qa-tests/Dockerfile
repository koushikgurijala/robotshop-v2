FROM loadimpact/k6:latest
COPY scripts /scripts/
USER root
RUN chmod +x -r /scripts
#ENTRYPOINT [ "k6", "run", "/scripts/test.js" ]