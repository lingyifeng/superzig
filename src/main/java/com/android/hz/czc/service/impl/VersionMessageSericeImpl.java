package com.android.hz.czc.service.impl;

import com.android.hz.czc.entity.VersionMessage;
import com.android.hz.czc.mapper.VersionMessageMapper;
import com.android.hz.czc.service.VersionMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class VersionMessageSericeImpl extends ServiceImpl<VersionMessageMapper, VersionMessage> implements VersionMessageService {
}
