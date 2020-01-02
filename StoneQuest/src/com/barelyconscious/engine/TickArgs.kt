package com.barelyconscious.engine

class PerformanceData(
    val framesPerSecond: Int
)

class TickArgs(
    val deltaTime: Double,
    val performanceData: PerformanceData
)
