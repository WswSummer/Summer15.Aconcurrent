package com.wsw.concurrent.interview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 写代码实现以下业务场景：
 * “小明和小张去中介找房子，小明要3室2厅的房子但租金不要超过6000，小李的要求是租金不超过1500的都能接受。中介说没问题，你们留个电话，有房子我通知你们”
 * 要求：
 * a.面向对象建模
 * b.使用自己熟悉的两种以上设计模式
 * @Author: wangsongwen
 * @Date: 2023/7/4 21:16
 */
// 房子类
@Data
@AllArgsConstructor
@NoArgsConstructor
class House {
    // 房子名称
    private String name;
    // 房间数
    private int rooms;
    // 厅数
    private int halls;
    // 租金
    private int rent;
}

// 观察者接口
interface Observer {
    void update(House house);
}

// 主题接口
interface Subject {
    // 注册观察者
    void registerObserver(Observer observer);

    // 移除观察者
    void removeObserver(Observer observer);

    // 通知观察者
    void notifyObservers();
}

// 中介类，实现主题接口
class Mediator implements Subject {
    private final List<Observer> observers;
    private final List<House> houses;

    public Mediator() {
        this.observers = new ArrayList<>();
        this.houses = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(findHouseForObserver(observer));
        }
    }

    public void addHouse(House house) {
        houses.add(house);
    }

    public void notifyObserversAfterAddingHouses() {
        notifyObservers();
    }

    private House findHouseForObserver(Observer observer) {
        for (House house : houses) {
            if (observer instanceof XiaoMing) {
                if (house.getRooms() == 3 && house.getHalls() == 2 && house.getRent() <= 6000) {
                    return house;
                }
            } else if (observer instanceof XiaoZhang) {
                if (house.getRent() <= 1500) {
                    return house;
                }
            }
        }
        return null;
    }
}

// 小明类，实现观察者接口
class XiaoMing implements Observer {
    private final String phoneNumber;

    public XiaoMing(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void update(House house) {
        if (house != null) {
            System.out.println(phoneNumber + "，小明收到房源信息：房子名称：" + house.getName() + "，房间数：" + house.getRooms() + "，厅数：" + house.getHalls() + "，租金：" + house.getRent());
        }
    }
}

// 小张类，实现观察者接口
class XiaoZhang implements Observer {
    private final String phoneNumber;

    public XiaoZhang(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void update(House house) {
        if (house != null) {
            System.out.println(phoneNumber + "，小张收到房源信息：房子名称：" + house.getName() + "，房间数：" + house.getRooms() + "，厅数：" + house.getHalls() + "，租金：" + house.getRent());
        }
    }
}

// 简单工厂类用于创建小明和小张对象
class PersonFactory {
    public static XiaoMing createXiaoMing(String phoneNumber) {
        return new XiaoMing(phoneNumber);
    }

    public static XiaoZhang createXiaoZhang(String phoneNumber) {
        return new XiaoZhang(phoneNumber);
    }
}

class HouseRentingExample {
    public static void main(String[] args) {
        Mediator mediator = new Mediator();

        XiaoMing xiaoMing = PersonFactory.createXiaoMing("123456789");
        XiaoZhang xiaoZhang = PersonFactory.createXiaoZhang("987654321");

        mediator.registerObserver(xiaoMing);
        mediator.registerObserver(xiaoZhang);

        House house1 = new House("房子1", 3, 2, 5000);
        House house2 = new House("房子2", 2, 1, 1000);
        House house3 = new House("房子3", 4, 3, 7000);

        mediator.addHouse(house1);
        mediator.addHouse(house2);
        mediator.addHouse(house3);

        mediator.notifyObserversAfterAddingHouses();
    }
}
