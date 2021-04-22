package com.braker.icontrol.purchase.apply.manager.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.icontrol.purchase.apply.manager.RegisterApplyMng;
import com.braker.icontrol.purchase.apply.model.RegisterApplyBasic;

@Service
@Transactional
public class RegisterApplyMngImpl extends BaseManagerImpl<RegisterApplyBasic> implements RegisterApplyMng{

	
}
