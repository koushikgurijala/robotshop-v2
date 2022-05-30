FROM blazemeter/taurus:latest

ENV PORT 8080
ENV HOST 0.0.0.0

COPY scripts /scripts/
USER root
RUN chmod +x -R /scripts
RUN ls -ltr /scripts
EXPOSE 8080
ENTRYPOINT ["bzt", "/scripts/test.yml"]