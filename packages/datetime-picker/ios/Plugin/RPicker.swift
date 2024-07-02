//
//  RPicker.swift
//  RPicker
//
//  Created by Rheyansh on 4/25/18.
//  Copyright © 2018 Raj Sharma. All rights reserved.
//

//  MIT License
//
//  Copyright (c) 2018 Raj Sharma
//
//  Permission is hereby granted, free of charge, to any person obtaining a copy
//  of this software and associated documentation files (the "Software"), to deal
//  in the Software without restriction, including without limitation the rights
//  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//  copies of the Software, and to permit persons to whom the Software is
//  furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in all
//  copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//  SOFTWARE.

import UIKit

@objc public class RPicker: NSObject {

    private static let sharedInstance = RPicker()
    private var isPresented = false

    @objc class func selectDate(title: String? = nil,
                                cancelText: String? = nil,
                                doneText: String = "Done",
                                datePickerMode: UIDatePicker.Mode = .date,
                                selectedDate: Date = Date(),
                                minDate: Date? = nil,
                                maxDate: Date? = nil,
                                locale: Locale? = nil,
                                style: DatetimePickerStyle = .inline,
                                theme: Theme = .auto,
                                completion: ((_ date: Date?, _ errorCode: ErrorCode) -> Void)?) {

        guard let vc = controller(title: title, cancelText: cancelText, doneText: doneText, datePickerMode: datePickerMode,
                                  selectedDate: selectedDate, minDate: minDate, maxDate: maxDate, locale: locale, style: style, theme: theme) else { return }

        vc.onDateSelected = { (selectedData) in
            completion?(selectedData, ErrorCode.none)
        }
        vc.onCanceled = { () in
            completion?(nil, ErrorCode.canceled)
        }
        vc.onBackdropDismissed = { () in
            completion?(nil, ErrorCode.dismissed)
        }
    }

    private class func controller(title: String? = nil,
                                  cancelText: String? = nil,
                                  doneText: String = "Done",
                                  datePickerMode: UIDatePicker.Mode = .date,
                                  selectedDate: Date = Date(),
                                  minDate: Date? = nil,
                                  maxDate: Date? = nil,
                                  locale: Locale? = nil,
                                  style: DatetimePickerStyle = .inline,
                                  theme: Theme = .auto) -> RPickerController? {

        if let cc = UIWindow.currentController {
            if RPicker.sharedInstance.isPresented == false {
                RPicker.sharedInstance.isPresented = true

                let vc = RPickerController(title: title, cancelText: cancelText, doneText: doneText, datePickerMode: datePickerMode,
                                           selectedDate: selectedDate, minDate: minDate, maxDate: maxDate, locale: locale, style: style, theme: theme)

                vc.modalPresentationStyle = .overCurrentContext
                vc.modalTransitionStyle = .crossDissolve
                cc.present(vc, animated: true, completion: nil)

                vc.onWillDismiss = {
                    RPicker.sharedInstance.isPresented = false
                }

                return vc
            }
        }

        return nil
    }
}

private extension UIView {

    func pinConstraints(_ byView: UIView, left: CGFloat? = nil, right: CGFloat? = nil, top: CGFloat? = nil, bottom: CGFloat? = nil, height: CGFloat? = nil, width: CGFloat? = nil) {

        translatesAutoresizingMaskIntoConstraints = false

        if let left = left { leftAnchor.constraint(equalTo: byView.leftAnchor, constant: left).isActive = true }
        if let right = right { rightAnchor.constraint(equalTo: byView.rightAnchor, constant: right).isActive = true }
        if let top = top { topAnchor.constraint(equalTo: byView.topAnchor, constant: top).isActive = true }
        if let bottom = bottom { bottomAnchor.constraint(equalTo: byView.bottomAnchor, constant: bottom).isActive = true }
        if let height = height { heightAnchor.constraint(equalToConstant: height).isActive = true }
        if let width = width { widthAnchor.constraint(equalToConstant: width).isActive = true }
    }

    func surroundConstraints(_ byView: UIView, left: CGFloat = 0, right: CGFloat = 0, top: CGFloat = 0, bottom: CGFloat = 0) {
        pinConstraints(byView, left: left, right: right, top: top, bottom: bottom)
    }
}

class RPickerController: UIViewController {

    // MARK: - Public closuers
    var onDateSelected: ((_ date: Date) -> Void)?
    var onCanceled: (() -> Void)?
    var onBackdropDismissed: (() -> Void)?
    var onWillDismiss: (() -> Void)?

    // MARK: - Public variables
    var selectedDate = Date()
    var maxDate: Date?
    var minDate: Date?
    var titleText: String?
    var cancelText: String?
    var doneText: String = "Done"
    var locale: Locale?
    var datePickerMode: UIDatePicker.Mode = .date
    var datePickerStyle: DatetimePickerStyle = .inline
    var theme: Theme = .auto

    // MARK: - Private variables
    private let barViewHeight: CGFloat = 44
    private let pickerHeight: CGFloat = 216
    private let buttonWidth: CGFloat = 84
    private let lineHeight: CGFloat = 0.5
    private let buttonColor = UIColor(red: 72/255, green: 152/255, blue: 240/255, alpha: 1)
    private let lineColor = UIColor(red: 240/255, green: 240/255, blue: 240/255, alpha: 1)

    // MARK: - Init
    init(title: String? = nil,
         cancelText: String? = nil,
         doneText: String = "Done",
         datePickerMode: UIDatePicker.Mode = .date,
         selectedDate: Date = Date(),
         minDate: Date? = nil,
         maxDate: Date? = nil,
         locale: Locale? = nil,
         style: DatetimePickerStyle = .inline,
         theme: Theme = .auto) {

        self.titleText = title
        self.cancelText = cancelText
        self.doneText = doneText
        self.datePickerMode = datePickerMode
        self.selectedDate = selectedDate
        self.minDate = minDate
        self.maxDate = maxDate
        self.locale = locale
        self.datePickerStyle = style
        self.theme = theme

        super.init(nibName: nil, bundle: nil)

        initialSetup()
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func traitCollectionDidChange(_ previousTraitCollection: UITraitCollection?) {
        // Trait collection has already changed
    }

    override func willTransition(to newCollection: UITraitCollection, with coordinator: UIViewControllerTransitionCoordinator) {
        // Trait collection will change. Use this one so you know what the state is changing to.
        if #available(iOS 12.0, *) {
            if newCollection.userInterfaceStyle != traitCollection.userInterfaceStyle {
                if newCollection.userInterfaceStyle == .dark {
                    setUpThemeMode(theme: theme, systemIsDark: true)
                } else {
                    setUpThemeMode(theme: theme, systemIsDark: false)
                }
            }
        } else {
            setUpThemeMode(theme: .light, systemIsDark: false)
        }
    }

    // MARK: - Private functions
    private func initialSetup() {

        view.backgroundColor = UIColor.clear
        let bgView = transView
        view.addSubview(bgView)
        bgView.surroundConstraints(view)

        // Stack View
        stackView.addArrangedSubview(lineLabel)
        stackView.addArrangedSubview(toolBarView)
        stackView.addArrangedSubview(lineLabel)

        var height = barViewHeight + (2*lineHeight)

        stackView.addArrangedSubview(datePicker)
        if #available(iOS 14.0, *) {
            if datePickerStyle == .wheel {
                height += pickerHeight
            } else {
                if datePicker.datePickerMode == .dateAndTime {
                    height += 468
                } else if datePicker.datePickerMode == .date {
                    height += 386
                } else {
                    height += pickerHeight
                }
            }
        } else {
            datePickerStyle = .wheel
            height += pickerHeight
        }

        self.view.addSubview(stackView)

        stackView.pinConstraints(view, left: 0, right: 0, bottom: 0, height: height)

        if #available(iOS 12.0, *) {
            if traitCollection.userInterfaceStyle == .dark {
                setUpThemeMode(theme: theme, systemIsDark: true)
            } else {
                setUpThemeMode(theme: theme, systemIsDark: false)
            }
        } else {
            setUpThemeMode(theme: .light, systemIsDark: false)
        }
    }

    private func setUpThemeMode(theme: Theme, systemIsDark: Bool) {
        switch theme {
        case .light:
            applyLightTheme()
            case .dark:
            applyDarkTheme()
        case .auto:
            if systemIsDark {
                applyDarkTheme()
            } else {
                applyLightTheme()
            }
        }
    }

    private func applyLightTheme() {
        titleLabel.textColor = UIColor.darkGray
        stackView.backgroundColor = UIColor.white
        transView.backgroundColor = UIColor(white: 0.1, alpha: 0.3)
        datePicker.backgroundColor = UIColor.white
        datePicker.setValue(UIColor.black, forKey: "textColor")
        datePicker.overrideUserInterfaceStyle = .light
        toolBarView.backgroundColor = UIColor.white
    }

    private func applyDarkTheme() {
        titleLabel.textColor = UIColor.white
        stackView.backgroundColor = UIColor.black
        transView.backgroundColor = UIColor(white: 1, alpha: 0.3)
        datePicker.backgroundColor = UIColor.black
        datePicker.setValue(UIColor.white, forKey: "textColor")
        datePicker.overrideUserInterfaceStyle = .dark
        toolBarView.backgroundColor = UIColor.black
    }

    private func dismissVC() {
        onWillDismiss?()
        dismiss(animated: true, completion: nil)
    }

    @objc func handleTap() {
        onBackdropDismissed?()
        dismissVC()
    }

    // MARK: - Private properties

    private lazy var transView: UIView = {
        let vw = UIView()
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.handleTap))
        vw.addGestureRecognizer(tapGesture)
        vw.isUserInteractionEnabled = true
        return vw
    }()

    private lazy var stackView: UIStackView = {
        let sv = UIStackView()
        sv.axis = NSLayoutConstraint.Axis.vertical
        sv.distribution = UIStackView.Distribution.fill
        sv.alignment = UIStackView.Alignment.center
        sv.spacing = 0.0
        return sv
    }()

    private lazy var datePicker: UIDatePicker = {
        let picker = UIDatePicker()
        picker.pinConstraints(view, width: view.frame.width)
        picker.minimumDate = minDate
        picker.maximumDate = maxDate
        picker.date = selectedDate
        picker.datePickerMode = datePickerMode
        if let locale = locale {
            picker.locale = locale
        }

        if #available(iOS 14, *) {
            if datePickerStyle == .wheel {
                picker.preferredDatePickerStyle = .wheels
            } else {
                picker.preferredDatePickerStyle = .inline
            }
        }

        return picker
    }()

    private lazy var toolBarView: UIView = {

        let barView = UIView()
        barView.pinConstraints(view, height: barViewHeight, width: view.frame.width)

        let doneButton = self.doneButton
        let cancelButton = self.cancelButton

        barView.addSubview(doneButton)
        barView.addSubview(cancelButton)

        cancelButton.pinConstraints(barView, left: 0, top: 0, bottom: 0, width: buttonWidth)
        doneButton.pinConstraints(barView, right: 0, top: 0, bottom: 0, width: buttonWidth)

        if let text = titleText {
            let titleLabel = self.titleLabel
            titleLabel.text = text
            barView.addSubview(titleLabel)
            titleLabel.surroundConstraints(barView, left: buttonWidth, right: -buttonWidth)
        }

        doneButton.setTitle(doneText, for: .normal)

        if let text = cancelText {
            cancelButton.setTitle(text, for: .normal)
        } else {
            cancelButton.isHidden = true
        }

        return barView
    }()

    private lazy var lineLabel: UILabel = {
        let label = UILabel()
        label.backgroundColor = lineColor
        label.pinConstraints(view, height: lineHeight, width: view.frame.width)
        return label
    }()

    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .center
        label.font = UIFont(name: "HelveticaNeue-Medium", size: 14)
        label.numberOfLines = 2
        return label
    }()

    private lazy var doneButton: UIButton = {
        let button = UIButton()
        button.setTitleColor(buttonColor, for: .normal)
        button.titleLabel?.font = UIFont(name: "HelveticaNeue-Bold", size: 16)
        button.addTarget(self, action: #selector(onDoneButton), for: .touchUpInside)
        return button
    }()

    private lazy var cancelButton: UIButton = {
        let button = UIButton()
        button.setTitleColor(buttonColor, for: .normal)
        button.titleLabel?.font = UIFont(name: "HelveticaNeue-Bold", size: 16)
        button.addTarget(self, action: #selector(onCancelButton), for: .touchUpInside)
        return button
    }()

    @objc func onDoneButton(sender: UIButton) {
        onDateSelected?(datePicker.date)
        dismissVC()
    }

    @objc func onCancelButton(sender: UIButton) {
        onCanceled?()
        dismissVC()
    }
}

// MARK: - Private Extensions

private extension UIApplication {
    static var keyWindow: UIWindow? {
        if #available(iOS 13.0, *) {
            return UIApplication.shared.windows.filter {$0.isKeyWindow}.first
        } else {
            return UIApplication.shared.delegate?.window ?? nil
        }
    }
}

private extension UIWindow {

    static var currentController: UIViewController? {
        return UIApplication.keyWindow?.currentController
    }

    var currentController: UIViewController? {
        if let vc = self.rootViewController {
            return topViewController(controller: vc)
        }
        return nil
    }

    func topViewController(controller: UIViewController? = UIApplication.keyWindow?.rootViewController) -> UIViewController? {
        if let nc = controller as? UINavigationController {
            if nc.viewControllers.count > 0 {
                return topViewController(controller: nc.viewControllers.last!)
            } else {
                return nc
            }
        }
        if let tabController = controller as? UITabBarController {
            if let selected = tabController.selectedViewController {
                return topViewController(controller: selected)
            }
        }
        if let presented = controller?.presentedViewController {
            return topViewController(controller: presented)
        }
        return controller
    }
}
