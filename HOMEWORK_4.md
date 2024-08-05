# CPU bound test
### Before refactor code (hw04-gc)
| Memory flag for a JVM | Result (low is better)     |
|-----------------------|----------------------------|
| -Xms4m -Xmx4m         | Exception in thread "main" java.lang.OutOfMemoryError: Java heap space|
| -Xms2g -Xmx2g         | spend msec:303907, sec:303 |
| -Xms256m -Xmx2g       | spend msec:298267, sec:298 |
| -Xms256m -Xmx256m     | spend msec:297042, sec:297 |
| -Xms128m -Xmx256m     | spend msec:296198, sec:296 |
| -Xms64m -Xmx64m       | spend msec:294857, sec:294 |
| -Xms128m -Xmx128m     | spend msec:290001, sec:290 |
| -Xms32m -Xmx32m       | spend msec:261585, sec:261 |
| -Xms16m -Xmx32m       | spend msec:196791, sec:196 |
| -Xms16m -Xmx16m       | spend msec:170582, sec:170 |
| -Xms8m -Xmx16m        | spend msec:162360, sec:162 |
| -Xms4m -Xmx8m         | spend msec:131782, sec:131 |
| -Xms8m -Xmx8m         | spend msec:126827, sec:126 |

### After refactor code
| Memory flag for a JVM | Result (low is better)       |
|-----------------------|------------------------------|
| -Xms8m -Xmx8m         | spend msec:102016, sec:102   |
| -Xms16m -Xmx16m       | spend msec:100475, sec:100   |

# Memory bound test (hw04-gc2)
### Before refactor code
| Memory flag for a JVM | Result (low is better)    |
|-----------------------|---------------------------|
| -Xms256m -Xmx256m     | spend msec:45163, sec:45  |
| -Xms128m -Xmx128m     | spend msec:45914, sec:45  |
| -Xms1g -Xmx1g         | spend msec:44691, sec:44  |
| -Xms1g -Xmx2g         | spend msec:44827, sec:44  |
| -Xms2g -Xmx2g         | spend msec:37565, sec:37  |
| -Xms2g -Xmx3g         | spend msec:37488, sec:37  |
| -Xms3g -Xmx3g         | spend msec:33578, sec:33  |
| -Xms4g -Xmx4g         | spend msec:30288, sec:30  |
| -Xms5g -Xmx5g         | spend msec:26336, sec:26  |
| -Xms6g -Xmx6g         | spend msec:22705, sec:22  |
| -Xms10g -Xmx10g       | spend msec:12868, sec:12  |

### After refactor code
| Memory flag for a JVM | Result (low is better)  |
|-----------------------|-------------------------|
| -Xms64m -Xmx64        | spend msec:7468, sec:7  |
| -Xms128m -Xmx128m     | spend msec:7382, sec:7  |
| -Xms256m -Xmx256m     | spend msec:7188, sec:7  |
| -Xms256m -Xmx512m     | spend msec:7182, sec:7  |
| -Xms512m -Xmx512m     | spend msec:6398, sec:6  |
| -Xms1g -Xmx1g         | spend msec:5687, sec:5  |
| -Xms4g -Xmx4g         | spend msec:5062, sec:5  |
| -Xms3g -Xmx3g         | spend msec:4924, sec:4  |
| -Xms2g -Xmx2g         | spend msec:4705, sec:4  |