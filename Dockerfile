FROM ubuntu:latest
LABEL authors="team-fixwi"

ENTRYPOINT ["top", "-b"]