"""
Usage:
  python http-client.py journey_relay 0 ping.json [optional_pk]
  python http-client.py journey_relay 0 relay-local-3.json [optional_pk]
  python http-client.py journey_relay 0 relay-local-10.json [optional_pk]
  python http-client.py journey_relay 1 relay-planet-5.json [optional_pk]
  python http-client.py journey_relay all ping.json
  -
  python http-client.py journey_delete_all 0 delete_all.json
  -
  python http-client.py journey_get_by_pk 0 [pk]
  python http-client.py journey_get_by_id_and_pk [id] [pk]
  -
  python http-client.py journey_get_by_elapsed 0 [ms]
  -
  python http-client.py health_alive x
  python http-client.py health_ready x
  -
  python http-client.py create_config_json
"""

import json
import os
import sys
import time

import requests

from docopt import docopt

VERSION = 'v20191218b'

class AtwHttpClient:

    def __init__(self):
        self.u = None  # the current url
        self.r = None  # the current requests response object
        self.config = dict()
        self.user_agent = {'User-agent': 'Mozilla/5.0'}

    def initialize(self):
        self.config = self.load_json_file(self.config_json_filename())

    def health_ready(self, target):
        base_url = self.base_url_for_target(target)
        endpoint = '{}/health/ready'.format(base_url)
        self.execute_get(endpoint, {'persist': True})

    def health_alive(self, target):
        base_url = self.base_url_for_target(target)
        endpoint = '{}/health/alive'.format(base_url)
        self.execute_get(endpoint, {'save_to_file': True})

    def journey_relay(self, target, infile, pk):
        base_url = self.base_url_for_target(target)
        endpoint = '{}/journey/relay'.format(base_url)
        postobj  = self.load_json_file('journeys/{}'.format(infile))
        postobj['pk'] = pk
        self.execute_post(endpoint, postobj, {'save_to_file': True})

    def journey_delete_all(self, target, infile):
        base_url = self.base_url_for_target(target)
        endpoint = '{}/journey/delete_all'.format(base_url)
        postobj  = self.load_json_file('journeys/{}'.format(infile))
        self.execute_post(endpoint, postobj, {'save_to_file': True})

    def journey_get_by_id_and_pk(self, target, id, pk):
        base_url = self.base_url_for_target(target)
        endpoint = '{}/journey/getbyid/{}/pk/{}'.format(base_url, id, pk)
        self.execute_get(endpoint, {'save_to_file': True})

    def journey_get_by_pk(self, target, pk):
        base_url = self.base_url_for_target(target)
        endpoint = '{}/journey/getbypk/{}'.format(base_url, pk)
        self.execute_get(endpoint, {'save_to_file': True})

    def journey_get_by_elapsed(self, target, ms):
        base_url = self.base_url_for_target(target)
        endpoint = '{}/journey/getbyelapsed/{}'.format(base_url, ms)
        self.execute_get(endpoint, {'save_to_file': True})

    def execute_get(self, endpoint, opts):
        print('===')
        print('execute_get, endpoint: {}'.format(endpoint))
        r = requests.get(url=endpoint) 
        print('---')
        print('response: {}'.format(r))

        if r.status_code == 200:
            resp_obj = json.loads(r.text)
            print(json.dumps(resp_obj, sort_keys=False, indent=2))
            if 'save_to_file' in opts:
                p = dict()
                p['endpoint'] = endpoint
                p['resp'] = str(r)
                p['resp_obj'] = resp_obj
                self.write_json_file(p, 'tmp/get.json')

    def execute_post(self, endpoint, postobj, opts):
        print('===')
        print('execute_post, endpoint: {}'.format(endpoint))
        print(json.dumps(postobj, sort_keys=False, indent=2))
        print('')
        r = requests.post(url=endpoint, json=postobj) 
        print('---')
        print('response: {}'.format(r))

        if r.status_code == 200:
            resp_obj = json.loads(r.text)
            print(json.dumps(resp_obj, sort_keys=False, indent=2))
            if 'save_to_file' in opts:
                p = dict()
                p['endpoint'] = endpoint
                p['postobj']  = postobj
                p['resp'] = str(r)
                p['resp_obj'] = resp_obj
                self.write_json_file(p, 'tmp/post.json')

    def base_url_for_target(self, target):
        return self.config['targets'][target]

    def epoch(self):
        return time.time()
    
    def config_json_filename(self):
        return 'data/config.json'

    def load_config(self):
        with open(self.config_json_filename(), 'rt') as json_file:
            self.data = json.loads(str(json_file.read()))

    def create_config_json(self):
        data, targets = dict(), dict()
        data['targets'] = targets
        targets['0'] = 'http://localhost:3000'
        targets['1'] = 'http://cjoakim-atw-1-eastus.eastus.azurecontainer.io:80'
        targets['2'] = 'http://cjoakim-atw-2-northeurope.northeurope.azurecontainer.io:80'
        targets['3'] = 'http://cjoakim-atw-3-centralindia.centralindia.azurecontainer.io:80'
        targets['4'] = 'http://cjoakim-atw-4-japaneast.japaneast.azurecontainer.io:80'
        targets['5'] = 'http://cjoakim-atw-5-westus.westus.azurecontainer.io:80'
        self.write_json_file(data, self.config_json_filename())

    def write_json_file(self, obj, outfile):
        with open(outfile, 'wt') as f:
            f.write(json.dumps(obj, sort_keys=False, indent=2))
            print('file written: {}'.format(outfile))

    def load_json_file(self, infile):
        with open(infile, 'rt') as json_file:
            return json.loads(str(json_file.read()))

def all_targets():
    return '1,2,3,4,5'.split(',')

def print_options(msg):
    print(msg)
    arguments = docopt(__doc__, version=VERSION)
    print(arguments)


if __name__ == "__main__":
    # print(sys.argv)

    if len(sys.argv) > 1:
        func = sys.argv[1].lower()
        print('func: {}'.format(func))
        client = AtwHttpClient()
        client.initialize()

        if func == 'health_ready':
            target = sys.argv[2].lower()
            if target == 'all':
                for t in all_targets():
                    client.health_ready(t)
            else:
                client.health_ready(target)

        elif func == 'health_alive':
            target = sys.argv[2].lower()
            if target == 'all':
                for t in all_targets():
                    client.health_alive(t)
            else:
                client.health_alive(target)

        elif func == 'journey_get_by_id_and_pk':
            target = sys.argv[2].lower()
            id = sys.argv[3]
            pk = sys.argv[4]
            client.journey_get_by_id_and_pk(target, id, pk)

        elif func == 'journey_get_by_pk':
            target = sys.argv[2].lower()
            pk = sys.argv[3]
            client.journey_get_by_pk(target, pk)

        elif func == 'journey_get_by_elapsed':
            target = sys.argv[2].lower()
            ms = int(sys.argv[3])
            client.journey_get_by_elapsed(target, ms)

        elif func == 'journey_relay':
            target = sys.argv[2].lower()
            infile = sys.argv[3]
            if len(sys.argv) > 4:
                pk = sys.argv[4]
            else:
                pk = str(int(time.time()))
            if target == 'all':
                for t in all_targets():
                    client.journey_relay(t, infile, pk)
            else:
                client.journey_relay(target, infile, pk)

        elif func == 'journey_delete_all':
            target = sys.argv[2].lower()
            infile = sys.argv[3]
            client.journey_delete_all(target, infile)

        elif func == 'create_config_json':
            client.create_config_json()
    
        else:
            print_options('Error: invalid function: {}'.format(func))
    else:
        print_options('Error: no function argument provided.')