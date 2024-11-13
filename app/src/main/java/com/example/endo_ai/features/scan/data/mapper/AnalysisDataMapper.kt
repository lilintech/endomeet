package com.example.endo_ai.features.scan.data.mapper

import com.example.endo_ai.features.scan.data.dto.AnalysisDataDto
import com.example.endo_ai.features.scan.data.dto.AnalysisResultDto
import com.example.endo_ai.features.scan.domain.model.AnalysisData
import com.example.endo_ai.features.scan.domain.model.AnalysisResult

fun AnalysisResultDto.toAnalysisResult(): AnalysisResult {
    return AnalysisResult(
        findings = findings,
        summary = summary,
        recommendation = recommendation

    )
}

fun AnalysisResult.toAnalysisResultDto(): AnalysisResultDto {
    return AnalysisResultDto(
        findings = findings,
        summary = summary,
        recommendation = recommendation

    )
}

//fun AnalysisData.toAnalysisDataDto(): AnalysisDataDto {
//    return AnalysisDataDto(
//        result = result.toAnalysisResultDto()
//    )
//}
//
//fun AnalysisDataDto.toAnalysisData(): AnalysisData {
//    return AnalysisData(
//        result = result.toAnalysisResult()
//    )
//}