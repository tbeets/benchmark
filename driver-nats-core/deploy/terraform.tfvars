public_key_path = "~/.ssh/nats_aws.pub"
region          = "us-west-2"
ami             = "ami-0892d3c7ee96c0bf7" // Ubuntu Server 20.04 LTS (HVM), SSD Volume Type 

instance_types = {
  "natsserver"      = "t3.xlarge"
  "natsclient"      = "t3.xlarge"
}

num_instances = {
  "natsclient"      = 3 
  "natsserver"      = 2 
}
