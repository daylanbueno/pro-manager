FROM nginx:stable

COPY docker/nginx/default.conf /etc/nginx/conf.d/
COPY docker/nginx/proxy.conf /etc/nginx/

ADD build /usr/share/nginx/html

RUN chmod g+rwx /var/cache/nginx /var/run /var/log/nginx


EXPOSE 80

# comment user directive as master process is run as user in OpenShift anyhow
RUN sed -i.bak 's/^user/#user/' /etc/nginx/nginx.conf
