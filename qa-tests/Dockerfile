FROM loadimpact/k6:latest
COPY scripts /scripts/
ENTRYPOINT [ "k6", "run", "/scripts/test.js" ]