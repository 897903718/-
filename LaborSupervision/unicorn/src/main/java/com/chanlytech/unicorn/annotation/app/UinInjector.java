package com.chanlytech.unicorn.annotation.app;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 注解注入器
 *
 * @author YangQiang
 */
public class UinInjector
{
    private static UinInjector instance;

    private UinInjector()
    {

    }

    public static UinInjector getInstance()
    {
        if (instance == null)
        {
            instance = new UinInjector();
        }
        return instance;
    }

    public void inJectAll(Object object)
    {
        if (!(object instanceof Activity) || !(object instanceof Fragment))
        {
            throw new RuntimeException("参数类型不对");
        }
        Field[] fields = object.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0)
        {
            for (Field field : fields)
            {
                if (field.isAnnotationPresent(UinInjectView.class))
                {
                    injectView(object, field);
                }
                else if (field.isAnnotationPresent(UinInjectResource.class))
                {
                    injectResource(object, field);
                }
                else if (field.isAnnotationPresent(UinInject.class))
                {
                    inject(object, field);
                }
            }
        }
    }

    private void inject(Object object, Field field)
    {
        try
        {
            field.setAccessible(true);
            field.set(object, field.getType().newInstance());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void injectView(Object object, Field field)
    {
        if (field.isAnnotationPresent(UinInjectView.class))
        {
            UinInjectView viewInject = field.getAnnotation(UinInjectView.class);
            int viewId = viewInject.id();
            try
            {
                field.setAccessible(true);
                findViewById(field, object, viewId);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过ID返回视图
     *
     * @param field
     * @param object
     * @param viewId
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private void findViewById(Field field, Object object, int viewId) throws IllegalAccessException, IllegalArgumentException
    {
        if (object instanceof Activity)
        {
            Activity activity = (Activity) object;
            field.set(activity, activity.findViewById(viewId));
        }
        else if (object instanceof Fragment)
        {
            Fragment fragment = (Fragment) object;
            View view = fragment.getView().findViewById(viewId);
            field.set(fragment, view);
            addOnClick(field, object, view);
        }
    }

    /**
     * 添加点击事件
     *
     * @param field
     * @param object
     * @param view
     */
    private void addOnClick(Field field, Object object, View view)
    {
        boolean isClick = field.getAnnotation(UinInjectView.class).isClick();
        if (isClick)
        {
            if (object instanceof View.OnClickListener)
            {
                view.setOnClickListener((View.OnClickListener) object);
            }
        }
    }

    private void injectResource(Object object, Field field)
    {
        if (field.isAnnotationPresent(UinInjectResource.class))
        {
            UinInjectResource resourceJect = field.getAnnotation(UinInjectResource.class);
            int resourceID = resourceJect.id();
            try
            {
                Activity activity = null;
                if (object instanceof Activity)
                {
                    activity = (Activity) object;
                }
                else if (object instanceof Fragment)
                {
                    activity = ((Fragment) object).getActivity();
                }
                field.setAccessible(true);
                Resources resources = activity.getResources();
                String type = resources.getResourceTypeName(resourceID);
                if (type.equalsIgnoreCase("string"))
                {
                    field.set(activity, activity.getResources().getString(resourceID));
                }
                else if (type.equalsIgnoreCase("drawable"))
                {
                    field.set(activity, activity.getResources().getDrawable(resourceID));
                }
                else if (type.equalsIgnoreCase("layout"))
                {
                    field.set(activity, activity.getResources().getLayout(resourceID));
                }
                else if (type.equalsIgnoreCase("array"))
                {
                    if (field.getType().equals(int[].class))
                    {
                        field.set(activity, activity.getResources().getIntArray(resourceID));
                    }
                    else if (field.getType().equals(String[].class))
                    {
                        field.set(activity, activity.getResources().getStringArray(resourceID));
                    }
                    else
                    {
                        field.set(activity, activity.getResources().getStringArray(resourceID));
                    }

                }
                else if (type.equalsIgnoreCase("color"))
                {
                    if (field.getType().equals(Integer.TYPE))
                    {
                        field.set(activity, activity.getResources().getColor(resourceID));
                    }
                    else
                    {
                        field.set(activity, activity.getResources().getColorStateList(resourceID));
                    }

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void injectWithAnnotation(Object activity, Field[] fields)
    {
        if (fields == null || fields.length <= 0)
        {
            return;
        }
        for (Field field : fields)
        {
            if (field.isAnnotationPresent(UinInject.class))
            {
                inject(activity, field);
            }
        }
    }

    private void injectViewWithAnnotation(Object activity, Field[] fields)
    {
        if (fields == null || fields.length <= 0)
        {
            return;
        }
        for (Field field : fields)
        {
            if (field.isAnnotationPresent(UinInjectView.class))
            {
                injectView(activity, field);
            }
        }
    }

    public void inject(Object activity)
    {
        Field[] fields = activity.getClass().getDeclaredFields();
        injectWithAnnotation(activity, fields);
        Class tmpActivity = activity.getClass();
        Class<?> superclass = null;
        while ((superclass = tmpActivity.getSuperclass()) != null)
        {
            if (superclass.getName().equals(Object.class.getName()))
            {
                break;
            }
            Field[] superFields = superclass.getDeclaredFields();
            injectWithAnnotation(superclass.cast(activity), superFields);
            tmpActivity = superclass;
        }
    }

    public void injectView(Object activity)
    {
        Field[] fields = activity.getClass().getDeclaredFields();
        injectViewWithAnnotation(activity, fields);
        Class tmpActivity = activity.getClass();
        Class<?> superclass = null;
        while ((superclass = tmpActivity.getSuperclass()) != null)
        {
            if (superclass.getName().equals(Object.class.getName()))
            {
                break;
            }
            Field[] superFields = superclass.getDeclaredFields();
            injectViewWithAnnotation(superclass.cast(activity), superFields);
            tmpActivity = superclass;
        }
    }

    public void injectResource(Object activity)
    {
        Field[] fields = activity.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0)
        {
            for (Field field : fields)
            {
                if (field.isAnnotationPresent(UinInjectResource.class))
                {
                    injectResource(activity, field);
                }
            }
        }
    }

    /**
     * 手动注入
     *
     * @param obj
     *         包含注解的类
     * @param view
     *         包含被注解的控件的View，能通过View.findViewById找到
     */
    public static void manualInjectView(final Object obj, final View view)
    {
        Field[] fields = obj.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0)
        {
            for (Field field : fields)
            {
                if (field.isAnnotationPresent(UinInjectView.class))
                {
                    final UinInjectView viewInject = field.getAnnotation(UinInjectView.class);
                    int viewId = viewInject.id();
                    try
                    {
                        field.setAccessible(true);
                        View viewById = view.findViewById(viewId);
                        field.set(obj, viewById);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 设置点击事件
     *
     * @param obj
     *         包含点击事件的类
     * @param view
     *         被点击的View
     * @param click
     *         点击事件触发的方法名称
     */
    private static void setOnClick(final Object obj, final View view, final String click)
    {
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Method method = obj.getClass().getMethod(click, View.class);
                    method.invoke(obj, v);
                }
                catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
