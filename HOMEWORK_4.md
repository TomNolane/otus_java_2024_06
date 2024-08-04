### Before refactor code
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
| Memory flag for a JVM | Result (low is better)     |
|-----------------------|----------------------------|
| -Xms8m -Xmx8m         | spend msec:102016, sec:102 |
| -Xms16m -Xmx16m       | spend msec:101387, sec:101 |