import torch
import torch.nn as nn
import torchvision.datasets as dsets
import torchvision.transforms as transforms
import torch.nn.functional as F
from torch.autograd import Variable

class LogisticRegression(torch.nn.Module):

    def __init__(self, num_features):
        super(LogisticRegression, self).__init__()
        self.linear = torch.nn.Linear(num_features, 1)
        self.linear.weight.data.zero_()
        self.linear.bias.data.zero_()

    def forward(self, x):
        logits = self.linear(x)
        probas = F.sigmoid(logits)
        return probas