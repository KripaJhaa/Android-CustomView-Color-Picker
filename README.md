# Android-CustomView-Color-Picker

#### *Color Picker work With any image and pick Color of the Image Accordingly*

> Attributes Like
* Background Image

`of which of You need to Pick the color.`
* Cusror Image
* Cursor Width
* GuideLine If Required 
`Guideline is a Horizontal or Vertical line corresponding to the cusor hover.`

* Cursor Color
* Attributes for Ripple Effect 

```xml
android:background="?attr/selectableItemBackgroundBorderless

android:foreground="?attr/selectableItemBackgroundBorderless
```



>UI attribute to add for using the custom-color-picker

```xml
<com.example.kripajha.customviewdemo.Views.CustomView
       android:id="@+id/customView"
       android:layout_width="240dp"
       android:layout_height="400dp"
       android:layout_margin="30dp"
       app:SetHorizantalLine="true"
       app:SetVerticalLine="true"
       android:background="?attr/selectableItemBackgroundBorderless"
       android:foreground="?attr/selectableItemBackgroundBorderless"
       android:clickable="true"
       app:backGround_image="@drawable/colorpickerrectangular"
       app:circle_image="@drawable/cursor"
       app:circle_radius="20dp"
       app:rectangle_breadth="1dp"
       app:rectangle_color="#9ee2dee3" />
```

> **Working Demo of the Above Sample**

![Alt Text](https://im4.ezgif.com/tmp/ezgif-4-aa45b4e5bf.gif)


