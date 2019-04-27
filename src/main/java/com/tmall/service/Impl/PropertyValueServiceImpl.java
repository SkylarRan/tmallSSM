package com.tmall.service.Impl;

import com.tmall.mapper.ProductMapper;
import com.tmall.mapper.PropertyMapper;
import com.tmall.mapper.PropertyValueMapper;
import com.tmall.pojo.Product;
import com.tmall.pojo.Property;
import com.tmall.pojo.PropertyValue;
import com.tmall.pojo.PropertyValueExample;
import com.tmall.service.PropertyService;
import com.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Autowired
    PropertyService propertyService;

    @Override
    public void update(PropertyValue pv) {
        propertyValueMapper.updateByPrimaryKeySelective(pv);
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria()
                .andPtidEqualTo(ptid)
                .andPidEqualTo(pid);
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(example);
        if(pvs.isEmpty()){
            return null;
        }
        return pvs.get(0);
    }

    @Override
    //根据产品id获取所有属性值
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");
        List<PropertyValue> result = propertyValueMapper.selectByExample(example);
        //为每个属性值设置属性
        for(PropertyValue pv : result){
            Property p = propertyService.get(pv.getPtid());
            pv.setProperty(p);
        }
        return result;
    }

    @Override
    //初始化产品，为其设置属性和属性值
    //每个分类对应着商品和属性
    public void init(Product p) {
        List<Property> ps = propertyService.list(p.getCid());
        for(Property pt : ps){
            //根据产品pid和属性ptid查找属性值
            PropertyValue pv = get(pt.getId(), p.getId());

            if(null == pv){
                pv = new PropertyValue();
                pv.setPtid(pt.getId());
                pv.setPid(p.getId());
                propertyValueMapper.insert(pv);
            }
        }
    }
}
