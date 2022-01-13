# Resource Scenarios for Benchmarking

Notes:
* JS persistence is _file_
* explicit client ack
* replicas=#servers unless otherwise remarked
* "k" is 1024 and "M" is 1024x1024
* Pub and Sub rates are rounded to nearest MB
* NATS Server 2.6.6 unless otherwise remarked
* nats.java 2.13.1 unless otherwise remarked
* NATS CLI 0.28 (nats.go 1.13.0) unless otherwise remarked
* NATS CLI runs of 10M messages for 1kB and 4kB message size, 1M messages for 64kB message size

Scenario | pubs-subs-topics-msgsize-type | Server (natsserver) | Client (natsclient) | # Producer | # Consumer | Pub MB/s | Sub MB/s |
:--------|:------------|:-----------|:-----------|:---------|:---------|:---------|:---------
1 | 1-1-1-1kB-benchmark-jspush | 2x i3.large | 3x m6i.large | 1 | 1 | 15 | 15 |
1.1 | 1-1-1-1kB-benchmark-nonjs (driver-nats-core) | 2x i3.large | 3x m6i.large | 1 | 1 |  |  |
1.2 | 1-1-1-1kB-clibench-nonjs | 2x i3.large | 3x m6i.large | 1 | 1 | 300 | 300 |
1.3 | 1-1-1-1kB-clibench-jspush | 2x i3.large | 3x m6i.large | 1 | 1 | 29 | 29 |
1.4 | 1-1-1-1kB-clibench-jspull | 2x i3.large | 3x m6i.large | 1 | 1 | 15 | 15 |
2 | 1-1-1-4kB-benchmark-jspush | 2x i3.large | 3x m6i.large | 1 | 1 | 40  | 40 |
2.1 | 1-1-1-4kB-benchmark-nonjs (driver-nats-core) | 2x i3.large | 3x m6i.large | 1 | 1 |  |  |
2.2 | 1-1-1-4kB-clibench-nonjs | 2x i3.large | 3x m6i.large | 1 | 1 | 390 | 390 |
2.3 | 1-1-1-4kB-clibench-jspush | 2x i3.large | 3x m6i.large | 1 | 1 | 50 | 50 |
2.4 | 1-1-1-4kB-clibench-jspull | 2x i3.large | 3x m6i.large | 1 | 1 | 37 | 37 |
3 | 1-1-1-64kB-benchmark-jspush | 2x i3.large | 3x m6i.large | 1 | 1 | 47 | 47 |
3.1 | 1-1-1-64kB-benchmark-nonjs (driver-nats-core) | 2x i3.large | 3x m6i.large | 1 | 1 |  |  |
3.2 | 1-1-1-64kB-clibench-nonjs | 2x i3.large | 3x m6i.large | 1 | 1 | 336 | 324 |
3.3 | 1-1-1-64kB-clibench-jspush | 2x i3.large | 3x m6i.large | 1 | 1 | 78  | 60 |
3.4 | 1-1-1-64kB-clibench-jspull | 2x i3.large | 3x m6i.large | 1 | 1 | 86  | 86 |
4 | 3-3-3-1kB-benchmark-jspush | 2x i3.large | 3x m6i.large | 1 | 1 | |  |
4.1 | 3-3-3-4kB-benchmark-jspush | 2x i3.large | 3x m6i.large | 1 | 1 |   |  |
4.2 | 3-3-3-64kB-benchmark-jspush | 2x i3.large | 3x m6i.large | 1 | 1 |   |  |

# Infrastructure

Note: All scenarios run in AWS `us-west-2` unless otherwise noted.  All costs are _on-demand_ pricing.
### Operating System
AMI | Description
:---|:-----------
[ami-0892d3c7ee96c0bf7](https://us-west-2.console.aws.amazon.com/ec2/v2/home?region=us-west-2#ImageDetails:imageId=ami-0892d3c7ee96c0bf7) | Canonical, Ubuntu, 20.04 LTS, amd64 focal image build on 2021-11-29 |

### Hosts

Note: "-" for storage means only AMI OS disk mounted.

EC2 Image | Arch | Description | $ (USD/hr) | Memory (GiB) | vCPU | Cores/vCPU | Threads/Core | Sustained Clock (GHz) | Storage (GB) | Storage (Disks) | Storage (Type) | Network |
:---------|:-------|:------------|:-------|:------|:----------|:--------|:--------|:---------|:-----------|:---------|:---------|:---------
[i3.large](https://us-west-2.console.aws.amazon.com/ec2/v2/home?region=us-west-2#InstanceTypeDetails:instanceType=i3.large) | x86_64 | Storage Optimized | 0.156 | 15.3 | 2 | 1 | 2 | 2.3 | 475 | 1 | ssd | Up to 10 Gigabit|
[m6i.large](https://us-west-2.console.aws.amazon.com/ec2/v2/home?region=us-west-2#InstanceTypeDetails:instanceType=m6i.large) | x86_64 | General Purpose | 0.096 | 8 | 2 | 1 | 2 | 3.5 | - | - | - | Up to 12.5 Gigabit

