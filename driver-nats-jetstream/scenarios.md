# Resource Scenarios for Benchmarking

Note: JS persistence is _file_, explicit client ack, and replicas=#servers unless otherwise remarked

Scenario | Description | Server (natsserver) | Client (natsclient) | # Producer | # Consumer | Pub /s | Sub /s |
:--------|:------------|:-----------|:-----------|:---------|:---------|:---------|:---------
1 | 1-1-benchmark-1kB-jspush | 2x i3.large | 3x m6i.large | 1 | 1 | 15 MB | 15 MB |
1.1 | 1-1-benchmark-1kB-core (driver-nats-core) | 2x i3.large | 3x m6i.large | 1 | 1 |  |  |
1.2 | 1-1-clibench-1kB-core | 2x i3.large | 3x m6i.large | 1 | 1 | 300 MB  | 300 MB |
1.3 | 1-1-clibench-1kB-jspush | 2x i3.large | 3x m6i.large | 1 | 1 | 29 MB  | 29 MB |
1.4 | 1-1-clibench-1kB-jspull | 2x i3.large | 3x m6i.large | 1 | 1 | 15 MB  | 15 MB |
2 | 1-1-benchmark-4kB-jspush | 2x i3.large | 3x m6i.large | 1 | 1 | 15 MB | 15 MB |
2.1 | 1-1-benchmark-4kB-core (driver-nats-core) | 2x i3.large | 3x m6i.large | 1 | 1 |  |  |
2.2 | 1-1-clibench-4kB-core | 2x i3.large | 3x m6i.large | 1 | 1 |   |  |
2.3 | 1-1-clibench-4kB-jspush | 2x i3.large | 3x m6i.large | 1 | 1 |   |  |
2.4 | 1-1-clibench-4kB-jspull | 2x i3.large | 3x m6i.large | 1 | 1 |   |  |
3 | 1-1-benchmark-64kB-jspush | 2x i3.large | 3x m6i.large | 1 | 1 |  |  |
3.1 | 1-1-benchmark-64kB-core (driver-nats-core) | 2x i3.large | 3x m6i.large | 1 | 1 |  |  |
3.2 | 1-1-clibench-64kB-core | 2x i3.large | 3x m6i.large | 1 | 1 |   |  |
3.3 | 1-1-clibench-64kB-jspush | 2x i3.large | 3x m6i.large | 1 | 1 |   |  |
3.4 | 1-1-clibench-64kB-jspull | 2x i3.large | 3x m6i.large | 1 | 1 |   |  |

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

