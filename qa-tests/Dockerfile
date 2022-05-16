FROM loadimpact/k6:latest
COPY scripts /scripts/
USER root
RUN chmod +x -R /scripts
ENTRYPOINT [ "k6", "run", "/scripts/test.js" ]