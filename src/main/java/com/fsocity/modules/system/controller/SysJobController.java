package com.fsocity.modules.system.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import com.fsocity.modules.system.service.SysJobService;
import io.swagger.annotations.ApiOperation;
import com.fsocity.modules.system.entity.SysJob;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fsocity.framework.web.JsonResult;
import com.fsocity.framework.web.FieldErrorInfo;
import com.fsocity.framework.web.ResponseStatusEnum;
import com.fsocity.framework.util.ValidationUtils;

import java.util.List;

/**
 * <p>
 * 定时任务调度表 前端控制器
 * </p>
 *
 * @author Zail
 * @since 2022-02-18
 */
@RestController
@RequestMapping("/system/api/sysJob")
public class SysJobController {

@Autowired
private SysJobService sysJobService;

    @ApiOperation("列表")
    @GetMapping("/list")
    public JsonResult list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           SysJob form) {
        Page<SysJob> list = sysJobService.findAll(form, pageNum, pageSize);
        return JsonResult.ok(list);
    }

    @ApiOperation("详情")
    @GetMapping("/{id}")
    public JsonResult detail(@PathVariable Integer id) {
        SysJob sysJob = sysJobService.getById(id);
        return JsonResult.ok(sysJob);
    }

    @ApiOperation("保存")
    @PostMapping("/save")
    public JsonResult save(@RequestBody @Validated SysJob sysJob,
                           BindingResult bindingResult) {
        List<FieldErrorInfo> errors = ValidationUtils.getErrors(bindingResult);
        if (CollectionUtils.isNotEmpty(errors)) {
            String errorMsg = "字段：" + errors.get(0).getName() + "；错误信息:" + errors.get(0).getErrorMessage();
            return JsonResult.err(ResponseStatusEnum.VALIDATE_FAILED.getCode(), errorMsg);
        }
        boolean flag = sysJobService.save(sysJob);
        return JsonResult.ok(flag);
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{id}")
    public JsonResult delete(@PathVariable Integer id) {
        boolean flag = sysJobService.deleteById(id);
        return JsonResult.ok(flag);
    }

}

